package paging.android.example.com.pagingsample

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.sqlite.db.SimpleSQLiteQuery
import kotlinx.coroutines.delay

class MyPagingSource(
    private val dao: CheeseDao
) : PagingSource<Int, Cheese>() {

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cheese> {
        Log.d("ps", "Entered load()")
        val page = params.key?: 1

        val query: String = "SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC " +
                "LIMIT ${params.loadSize} OFFSET ${(page-1) * CheeseViewModel.PAGE_SIZE}"
        Log.d("ps", "limit ${params.loadSize}, offset ${(page -1) * CheeseViewModel.PAGE_SIZE}")

        val rawQuery: SimpleSQLiteQuery = SimpleSQLiteQuery(query, arrayOf<Cheese>())

        return try {
            Log.d("ps", "Entered try{} block")
//            var response = dao.rawGetAllCheeses(rawQuery)
            var response = tryQuery(rawQuery)
            Log.d("ps", "$response")

            if(response.size == 0) {
                response = tryQuery(rawQuery)
                Log.d("ps", "$response")
            }

            val nextKey = if(response.size == 0) {
                null
            } else {
                page + (params.loadSize / CheeseViewModel.PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    suspend fun tryQuery(rawQuery: SimpleSQLiteQuery) : List<Cheese> {
        Log.d("ps", "trying rawQuery")
        delay(1000)
        return dao.rawGetAllCheeses(rawQuery)
    }
    override fun getRefreshKey(state: PagingState<Int, Cheese>): Int? {
        return state.anchorPosition?.let {  anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }


}