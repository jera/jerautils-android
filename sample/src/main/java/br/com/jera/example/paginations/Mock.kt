package br.com.jera.example.paginations

import android.os.Handler
import br.com.jera.jerautils.paginations.interfaces.DataSource
import br.com.jera.jerautils.paginations.interfaces.DataSourceCallback
import br.com.jera.jerautils.paginations.interfaces.PaginationError
import br.com.jera.jerautils.paginations.interfaces.PaginationInfo
import java.util.*

/**
 * Created by daividsilverio on 02/12/16.
 */
data class PaginatedThings(val things: ArrayList<Thing>, val paginationInfo: PaginationInfo)
data class Thing(val name: String)

class FakeRequesterDataSource : DataSource<Thing> {
    companion object {
        private val things: ArrayList<Thing> = ArrayList((1..100).map { Thing("Thing $it") })
    }

    override fun fetchData(page: Int, pageSize: Int?, callback: DataSourceCallback<Thing>?) {
        Handler().postDelayed({
            val paginatedThings = request(page, pageSize ?: 10)
            if (Random().nextBoolean()) {
                callback?.onSuccess(paginatedThings.things, paginatedThings.paginationInfo)
            } else {
                callback?.onFailure(createPaginationError(), null)
            }
        }, 1500)
    }

    private fun createPaginationError(): PaginationError {
        return object : PaginationError {
            override fun getCause(): Exception {
                return Exception("Oh No Something went Wrong!!")
            }

            override fun getErrorMessage(): String {
                return cause.message?: "Damn!"
            }

        }
    }

    fun request(page: Int, requestedAmount: Int): PaginatedThings {
        val offset = if ((page - 1) * requestedAmount > FakeRequesterDataSource.things.size) FakeRequesterDataSource.things.size else (page - 1) * requestedAmount
        val amount = if ((offset + requestedAmount) > FakeRequesterDataSource.things.size) FakeRequesterDataSource.things.size - offset else requestedAmount
        val pagedThings = ArrayList(FakeRequesterDataSource.things.subList(offset, offset + amount))
        return PaginatedThings(pagedThings, createPaginationInfo(page, requestedAmount, FakeRequesterDataSource.things))
    }

    fun createPaginationInfo(page: Int, amount: Int, source: List<Any>): PaginationInfo {
        return object : PaginationInfo {
            override fun getCurrentPage(): Int {
                return page
            }

            override fun getNextPage(): Int {
                return page + 1
            }

            override fun getTotalItens(): Int {
                return source.size
            }

            override fun getTotalPages(): Int {
                return source.size / amount
            }
        }
    }
}