package service

import view.Refreshable

/**
 * This class handles multiple [Refreshable]s that are notified of the
 * changes to refresh using the [onAllRefreshable] function
 */
abstract class AbstractRefreshableService {

    private val refreshables = mutableListOf<Refreshable>()

    /**
     * A function that adds a [Refreshable] to the list which gets
     * called every tim ethe function [onAllRefreshable] is used
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        refreshables += newRefreshable
    }

    /**
     * This function executes every time when it is called the passed method on
     * all [Refreshable]s registered within the service class that extends
     * this [AbstractRefreshableService]
     */
    fun onAllRefreshable(method: Refreshable.() -> Unit) =
        refreshables.forEach { it.method() }

}