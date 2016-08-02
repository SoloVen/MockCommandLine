// **********************************************************
// Assignment3:
// UTORID user_name:kushtovm
//
// Author:Magomed-Lors Kushtov
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// *********************************************************

package a3Files;

import java.util.ArrayList;
import java.util.List;

import driver.MyParser;


/*
 * This program extracts and stores all of the contents of the wanted Author.
 * When creating an instance, the user inputs the HTML file from which to
 * extract the information from. The different methods then parse the data using
 * regular expressions and stores the relevant information into the proper
 * variables
 */
public class Author {

  String rawHTMLString;
  String HTMLfileName;
  List<String> authorName;
  List<String> numberOfCitations;
  List<String> i10IndexAfter2009;
  List<String> topPublications;
  List<String> coAuthorsList;
  static List<String> totalCoAuthors = new ArrayList<String>();
  static int numberOfTotalCoAuthors = 0;

  ExtractAuthorName name;
  ExtractNumberOfCitations citations;
  ExtractTopPublications publications;
  ExtractITenIndex itenIndex;
  ExtractTotalCitations totalPaperCitations;
  ExtractCoAuthors coAuthors;

  int numberOfCoAuthors;
  int totalCitations = 0;

  boolean exists = true;

  /*
   * Constructor that creates the author instance
   */
  public Author(String AuthorUrlString) {
    this.HTMLfileName = AuthorUrlString;
    try {
      this.rawHTMLString = RawHTMLContents.getHTML(AuthorUrlString);

      name = new ExtractAuthorName(this.rawHTMLString);
      this.authorName = name.extract();

      citations = new ExtractNumberOfCitations(this.rawHTMLString);
      this.numberOfCitations = citations.extract();

      publications = new ExtractTopPublications(this.rawHTMLString);
      this.topPublications = publications.extract();

      itenIndex = new ExtractITenIndex(this.rawHTMLString);
      this.i10IndexAfter2009 = itenIndex.extract();

      totalPaperCitations = new ExtractTotalCitations(this.rawHTMLString);
      for (String cts : totalPaperCitations.extract()) {
        this.totalCitations = (this.totalCitations + Integer.valueOf(cts));
      }

      coAuthors = new ExtractCoAuthors(this.rawHTMLString);
      this.coAuthorsList = coAuthors.extract();
      this.numberOfCoAuthors = coAuthorsList.size();

      numberOfTotalCoAuthors = numberOfTotalCoAuthors + numberOfCoAuthors;

      totalCoAuthors.addAll(coAuthorsList);
      java.util.Collections.sort(totalCoAuthors);

    } catch (Exception e) {
      System.out.print(AuthorUrlString + " file not found");
      this.exists = false;
    }
  }

  public static Author getObject(String name) {
    Author returnAuthor = null;
    for (Author author : MyParser.allAuthors) {
      if (author.HTMLfileName == name) {
        returnAuthor = author;
      }
    }
    return returnAuthor;

  }
}