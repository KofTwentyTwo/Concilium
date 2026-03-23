package com.kof22.concilium.integrations.github;


import java.util.ArrayList;
import java.util.List;


/*******************************************************************************
 ** Simple POJO representing a GitHub Issue as returned by the gh CLI.
 **
 ** Includes the issue number, title, body, state (open/closed), web URL,
 ** labels, and assignee.  Provides standard getters, setters, and fluent
 ** {@code with*} setters following the project convention.
 *******************************************************************************/
public class GitHubIssue
{
   private Integer number;
   private String  title;
   private String  body;
   private String  state;
   private String  url;
   private List<String> labels;
   private String  assignee;



   /*******************************************************************************
    ** Constructor
    *******************************************************************************/
   public GitHubIssue()
   {
      this.labels = new ArrayList<>();
   }



   /*******************************************************************************
    ** Getter for number
    *******************************************************************************/
   public Integer getNumber()
   {
      return (this.number);
   }



   /*******************************************************************************
    ** Setter for number
    *******************************************************************************/
   public void setNumber(Integer number)
   {
      this.number = number;
   }



   /*******************************************************************************
    ** Fluent setter for number
    *******************************************************************************/
   public GitHubIssue withNumber(Integer number)
   {
      this.number = number;
      return (this);
   }



   /*******************************************************************************
    ** Getter for title
    *******************************************************************************/
   public String getTitle()
   {
      return (this.title);
   }



   /*******************************************************************************
    ** Setter for title
    *******************************************************************************/
   public void setTitle(String title)
   {
      this.title = title;
   }



   /*******************************************************************************
    ** Fluent setter for title
    *******************************************************************************/
   public GitHubIssue withTitle(String title)
   {
      this.title = title;
      return (this);
   }



   /*******************************************************************************
    ** Getter for body
    *******************************************************************************/
   public String getBody()
   {
      return (this.body);
   }



   /*******************************************************************************
    ** Setter for body
    *******************************************************************************/
   public void setBody(String body)
   {
      this.body = body;
   }



   /*******************************************************************************
    ** Fluent setter for body
    *******************************************************************************/
   public GitHubIssue withBody(String body)
   {
      this.body = body;
      return (this);
   }



   /*******************************************************************************
    ** Getter for state
    *******************************************************************************/
   public String getState()
   {
      return (this.state);
   }



   /*******************************************************************************
    ** Setter for state
    *******************************************************************************/
   public void setState(String state)
   {
      this.state = state;
   }



   /*******************************************************************************
    ** Fluent setter for state
    *******************************************************************************/
   public GitHubIssue withState(String state)
   {
      this.state = state;
      return (this);
   }



   /*******************************************************************************
    ** Getter for url
    *******************************************************************************/
   public String getUrl()
   {
      return (this.url);
   }



   /*******************************************************************************
    ** Setter for url
    *******************************************************************************/
   public void setUrl(String url)
   {
      this.url = url;
   }



   /*******************************************************************************
    ** Fluent setter for url
    *******************************************************************************/
   public GitHubIssue withUrl(String url)
   {
      this.url = url;
      return (this);
   }



   /*******************************************************************************
    ** Getter for labels
    *******************************************************************************/
   public List<String> getLabels()
   {
      return (this.labels);
   }



   /*******************************************************************************
    ** Setter for labels
    *******************************************************************************/
   public void setLabels(List<String> labels)
   {
      this.labels = labels;
   }



   /*******************************************************************************
    ** Fluent setter for labels
    *******************************************************************************/
   public GitHubIssue withLabels(List<String> labels)
   {
      this.labels = labels;
      return (this);
   }



   /*******************************************************************************
    ** Getter for assignee
    *******************************************************************************/
   public String getAssignee()
   {
      return (this.assignee);
   }



   /*******************************************************************************
    ** Setter for assignee
    *******************************************************************************/
   public void setAssignee(String assignee)
   {
      this.assignee = assignee;
   }



   /*******************************************************************************
    ** Fluent setter for assignee
    *******************************************************************************/
   public GitHubIssue withAssignee(String assignee)
   {
      this.assignee = assignee;
      return (this);
   }
}
