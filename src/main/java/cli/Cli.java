package cli;

import main.RSSService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cli implements Runnable {
  private RSSService rssService = new RSSService();

  @Override
  public void run() {
    Scanner scanner = new Scanner(System.in);
    Matcher matcher = null;
    while (true) {
      String query = scanner.nextLine();

      if (query.matches("add +website")) {

        System.out.print("link: ");
        matcher = Pattern.compile(" *(\\S+)").matcher(scanner.nextLine());
        matcher.find();
        String webSiteLink = matcher.group(1);

        System.out.print("target class: ");
        matcher = Pattern.compile(" *(\\S+)").matcher(scanner.nextLine());
        matcher.find();
        String targetClass = matcher.group(1);

        System.out.print("date pattern: ");
        matcher = Pattern.compile(" *(.* *)$").matcher(scanner.nextLine());
        matcher.find();
        String datePattern = matcher.group(1);

        rssService.addWebSite(webSiteLink, targetClass, datePattern);
      } else if (query.matches(" *update")) {
        rssService.updateDataBase();
      } else if (query.matches("exit")) {
        return;
      }
    }
  }

  public static void main(String[] args) {
    new Thread(new Cli()).start();
  }
}
