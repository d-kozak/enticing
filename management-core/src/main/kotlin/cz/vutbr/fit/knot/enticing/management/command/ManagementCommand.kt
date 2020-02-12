package cz.vutbr.fit.knot.enticing.management.command


/**
 * High-level command for the management engine
 */
abstract class ManagementCommand {

    fun executeCommand() {
        execute()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    protected abstract fun execute()


}