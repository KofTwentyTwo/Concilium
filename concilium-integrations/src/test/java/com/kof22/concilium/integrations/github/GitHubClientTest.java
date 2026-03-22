package com.kof22.concilium.integrations.github;


import java.util.List;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/*******************************************************************************
 ** Unit tests for {@link GitHubClient}.
 **
 ** These tests verify command-building logic and JSON parsing without
 ** actually invoking the gh CLI.
 *******************************************************************************/
class GitHubClientTest
{
   private final GitHubClient client = new GitHubClient();



   /*******************************************************************************
    ** Verify that buildCommand produces the expected list of arguments.
    *******************************************************************************/
   @Test
   void testBuildCommand()
   {
      List<String> command = client.buildCommand("issue", "create", "--repo", "Kof22/Concilium");

      assertThat(command).containsExactly("gh", "issue", "create", "--repo", "Kof22/Concilium");
   }



   /*******************************************************************************
    ** Verify that buildCommand with no arguments produces just "gh".
    *******************************************************************************/
   @Test
   void testBuildCommandEmpty()
   {
      List<String> command = client.buildCommand();

      assertThat(command).containsExactly("gh");
   }



   /*******************************************************************************
    ** Verify that parseIssue correctly handles a single JSON object.
    *******************************************************************************/
   @Test
   void testParseIssueSingleObject() throws Exception
   {
      String json = """
         {
            "number": 42,
            "title": "Implement integration layer",
            "body": "Epic 8 implementation",
            "state": "OPEN",
            "url": "https://github.com/Kof22/Concilium/issues/42",
            "labels": [{"name": "enhancement"}, {"name": "epic-8"}],
            "assignees": [{"login": "james-maes"}]
         }
         """;

      GitHubIssue issue = client.parseIssue(json);

      assertThat(issue.getNumber()).isEqualTo(42);
      assertThat(issue.getTitle()).isEqualTo("Implement integration layer");
      assertThat(issue.getBody()).isEqualTo("Epic 8 implementation");
      assertThat(issue.getState()).isEqualTo("OPEN");
      assertThat(issue.getUrl()).isEqualTo("https://github.com/Kof22/Concilium/issues/42");
      assertThat(issue.getLabels()).containsExactly("enhancement", "epic-8");
      assertThat(issue.getAssignee()).isEqualTo("james-maes");
   }



   /*******************************************************************************
    ** Verify that parseIssue handles missing optional fields gracefully.
    *******************************************************************************/
   @Test
   void testParseIssueMissingFields() throws Exception
   {
      String json = """
         {
            "number": 1,
            "title": "Minimal issue"
         }
         """;

      GitHubIssue issue = client.parseIssue(json);

      assertThat(issue.getNumber()).isEqualTo(1);
      assertThat(issue.getTitle()).isEqualTo("Minimal issue");
      assertThat(issue.getBody()).isNull();
      assertThat(issue.getState()).isNull();
      assertThat(issue.getUrl()).isNull();
      assertThat(issue.getLabels()).isEmpty();
      assertThat(issue.getAssignee()).isNull();
   }



   /*******************************************************************************
    ** Verify that parseIssueList correctly parses a JSON array of issues.
    *******************************************************************************/
   @Test
   void testParseIssueList() throws Exception
   {
      String json = """
         [
            {"number": 1, "title": "First", "state": "OPEN", "labels": [], "assignees": []},
            {"number": 2, "title": "Second", "state": "CLOSED", "labels": [{"name": "bug"}], "assignees": []}
         ]
         """;

      List<GitHubIssue> issues = client.parseIssueList(json);

      assertThat(issues).hasSize(2);
      assertThat(issues.get(0).getNumber()).isEqualTo(1);
      assertThat(issues.get(0).getTitle()).isEqualTo("First");
      assertThat(issues.get(1).getNumber()).isEqualTo(2);
      assertThat(issues.get(1).getTitle()).isEqualTo("Second");
      assertThat(issues.get(1).getLabels()).containsExactly("bug");
   }



   /*******************************************************************************
    ** Verify that parseIssueList handles an empty JSON array.
    *******************************************************************************/
   @Test
   void testParseIssueListEmpty() throws Exception
   {
      String json = "[]";

      List<GitHubIssue> issues = client.parseIssueList(json);

      assertThat(issues).isEmpty();
   }



   /*******************************************************************************
    ** Verify GitHubIssue POJO getters, setters, and fluent setters.
    *******************************************************************************/
   @Test
   void testGitHubIssuePojo()
   {
      GitHubIssue issue = new GitHubIssue()
         .withNumber(99)
         .withTitle("Test title")
         .withBody("Test body")
         .withState("open")
         .withUrl("https://github.com/Kof22/Concilium/issues/99")
         .withLabels(List.of("bug", "critical"))
         .withAssignee("testuser");

      assertThat(issue.getNumber()).isEqualTo(99);
      assertThat(issue.getTitle()).isEqualTo("Test title");
      assertThat(issue.getBody()).isEqualTo("Test body");
      assertThat(issue.getState()).isEqualTo("open");
      assertThat(issue.getUrl()).isEqualTo("https://github.com/Kof22/Concilium/issues/99");
      assertThat(issue.getLabels()).containsExactly("bug", "critical");
      assertThat(issue.getAssignee()).isEqualTo("testuser");

      // Test standard setters
      issue.setNumber(100);
      issue.setTitle("Updated title");
      assertThat(issue.getNumber()).isEqualTo(100);
      assertThat(issue.getTitle()).isEqualTo("Updated title");
   }



   /*******************************************************************************
    ** Verify that parseIssue handles labels as plain text strings (not objects).
    *******************************************************************************/
   @Test
   void testParseIssueWithTextLabels() throws Exception
   {
      String json = """
         {
            "number": 10,
            "title": "Text labels",
            "labels": ["bug", "help-wanted"]
         }
         """;

      GitHubIssue issue = client.parseIssue(json);

      assertThat(issue.getLabels()).containsExactly("bug", "help-wanted");
   }



   /*******************************************************************************
    ** Verify that parseIssue handles assignees as plain text strings.
    *******************************************************************************/
   @Test
   void testParseIssueWithTextAssignee() throws Exception
   {
      String json = """
         {
            "number": 11,
            "title": "Text assignee",
            "assignees": ["someuser"]
         }
         """;

      GitHubIssue issue = client.parseIssue(json);

      assertThat(issue.getAssignee()).isEqualTo("someuser");
   }
}
