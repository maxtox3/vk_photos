package gusev.max.presentation.auth

import gusev.max.presentation.base.BaseResult
import gusev.max.presentation.base.model.TaskStatus


sealed class AuthResult : BaseResult {

    class SaveAuth(
        val taskStatus: TaskStatus
    ) : AuthResult() {

        companion object {

            internal fun success(): SaveAuth {
                return SaveAuth(
                        TaskStatus.SUCCESS
                )
            }

            internal fun failure(): SaveAuth {
                return SaveAuth(
                        TaskStatus.FAILURE
                )
            }

            internal fun inFlight(): SaveAuth {
                return SaveAuth(
                        TaskStatus.IN_WORK
                )
            }
        }
    }
}