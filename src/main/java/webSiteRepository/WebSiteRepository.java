package webSiteRepository;

import core.Core;
import core.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebSiteRepository extends Service
{
    private HashMap<Integer, NewsWebPageModel> webPageInformationHashMap = new HashMap<>();

    public WebSiteRepository(Core core)
    {
        super(core);
        core.setWebSiteRepository(this);
    }

    public void addWebSite(String link, String targetClass) throws SQLException, IOException, ParseException
    {
        if (getWebsite(link) != null)
            webPageInformationHashMap.remove(getWebsite(link).getId());

        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    WebsiteTableQueries.SELECT_WEBSITE_BY_LINK.toString());
            preparedStatement.setString(1, link);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.beforeFirst();

            NewsWebPageModel newsWebPageModel = new NewsWebPageModel(link, targetClass);
            if (!resultSet.next())
            {
                PreparedStatement preparedStatement1 =
                        conn.prepareStatement(WebsiteTableQueries.INSERT_INTO_TABLE.toString());
                preparedStatement1.setString(1, newsWebPageModel.getLink());
                preparedStatement1.setString(2, newsWebPageModel.getTargetClass());
                preparedStatement1.setString(3, newsWebPageModel.getDatePattern());
                preparedStatement1.execute();
            } else
            {
                PreparedStatement preparedStatement1 =
                        conn.prepareStatement(WebsiteTableQueries.UPDATE_TARGET_CLASS_BY_LINK.toString());
                preparedStatement1.setString(1, newsWebPageModel.getTargetClass());
                preparedStatement1.setString(2, newsWebPageModel.getDatePattern());
                preparedStatement1.setString(3,newsWebPageModel.getLink());
                preparedStatement1.execute();
            }
            webPageInformationHashMap.put(newsWebPageModel.getId(), newsWebPageModel);
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw e;
        }
    }

    public NewsWebPageModel getWebsite(int id) throws SQLException
    {
        if (webPageInformationHashMap.containsKey(id))
            return webPageInformationHashMap.get(id);
        else{
            try(Connection conn = core.getDatabaseConnectionPool().getConnection())
            {
                PreparedStatement preparedStatement = conn.prepareStatement(
                        WebsiteTableQueries.SELECT_WEBISTE_BY_ID.toString());
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();

                resultSet.beforeFirst();
                if (!resultSet.next())
                    return null;
                resultSet.first();
                NewsWebPageModel newsWebPageModel = new NewsWebPageModel(resultSet);
                webPageInformationHashMap.put(id, newsWebPageModel);
                return newsWebPageModel;
            } catch (SQLException e)
            {
                Core.getInstance().logToFile(e.getMessage());
                throw e;
            }
        }
    }

    public NewsWebPageModel getWebsite(String websiteLink) throws SQLException
    {
        try(Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement preparedStatement = conn.prepareStatement(
                    WebsiteTableQueries.SELECT_WEBSITE_BY_LINK.toString());
            preparedStatement.setString(1, websiteLink);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.beforeFirst();
            if(!resultSet.next())
                return null;

            resultSet.first();
            return getWebsite(resultSet.getInt("WID"));
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw e;
        }
    }

    public List<NewsWebPageModel> getWebsites() throws SQLException
    {
        try (Connection conn = core.getDatabaseConnectionPool().getConnection())
        {
            PreparedStatement preparedStatement =
                    conn.prepareStatement(WebsiteTableQueries.SELECT_ALL_WEBSITES.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<NewsWebPageModel> ans = new ArrayList<>();
            resultSet.beforeFirst();
            while (resultSet.next())
            {
                ans.add(getWebsite(resultSet.getInt("WID")));
            }
            return ans;
        } catch (SQLException e)
        {
            Core.getInstance().logToFile(e.getMessage());
            throw e;
        }
    }
}
