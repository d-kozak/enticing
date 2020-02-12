package cz.vutbr.fit.knot.enticing.management.command


/**
 * High-level command for the management engine
 */
abstract class ManagementCommand {

    internal open fun canExecute(): Boolean = true

    internal abstract fun execute()
}