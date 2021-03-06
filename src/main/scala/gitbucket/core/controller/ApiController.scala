package gitbucket.core.controller

import gitbucket.core.api._
import gitbucket.core.controller.api.{ApiOrganizationControllerBase, ApiRepositoryControllerBase, ApiUserControllerBase}
import gitbucket.core.service._
import gitbucket.core.util.Implicits._
import gitbucket.core.util._
import gitbucket.core.plugin.PluginRegistry

class ApiController
    extends ApiControllerBase
    with ApiOrganizationControllerBase
    with ApiRepositoryControllerBase
    with ApiUserControllerBase
    with RepositoryService
    with AccountService
    with ProtectedBranchService
    with IssuesService
    with LabelsService
    with MilestonesService
    with PullRequestService
    with CommitsService
    with CommitStatusService
    with RepositoryCreationService
    with IssueCreationService
    with HandleCommentService
    with WebHookService
    with WebHookPullRequestService
    with WebHookIssueCommentService
    with WikiService
    with ActivityService
    with PrioritiesService
    with OwnerAuthenticator
    with UsersAuthenticator
    with GroupManagerAuthenticator
    with ReferrerAuthenticator
    with ReadableUsersAuthenticator
    with WritableUsersAuthenticator

trait ApiControllerBase extends ControllerBase {

  /**
   * 404 for non-implemented api
   */
  get("/api/v3/*") {
    NotFound()
  }
  post("/api/v3/*") {
    NotFound()
  }
  put("/api/v3/*") {
    NotFound()
  }
  delete("/api/v3/*") {
    NotFound()
  }
  patch("/api/v3/*") {
    NotFound()
  }

  /**
   * https://developer.github.com/v3/#root-endpoint
   */
  get("/api/v3") {
    JsonFormat(ApiEndPoint())
  }

  /**
   * @see https://developer.github.com/v3/rate_limit/#get-your-current-rate-limit-status
   * but not enabled.
   */
  get("/api/v3/rate_limit") {
    contentType = formats("json")
    // this message is same as github enterprise...
    org.scalatra.NotFound(ApiError("Rate limiting is not enabled."))
  }

  /**
   * non-GitHub compatible API for listing plugins
   */
  get("/api/v3/gitbucket/plugins") {
    PluginRegistry().getPlugins().map { ApiPlugin(_) }
  }
}
