package com.tifone.tnews.base

interface IView<T> {
    fun setPresenter(presenter: T?)
}