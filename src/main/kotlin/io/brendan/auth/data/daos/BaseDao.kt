package io.brendan.auth.data.daos

interface BaseDao<T, ID> {
    fun insert(data: T): Int
    fun insert(data: List<T>): Int
    fun delete(data: T): Int
    fun findById(id: ID): T
    fun findAll(): List<T>
}