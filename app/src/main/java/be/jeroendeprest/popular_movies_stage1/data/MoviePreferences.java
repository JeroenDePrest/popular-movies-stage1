package be.jeroendeprest.popular_movies_stage1.data;

/**
 * Created by jeroe on 06/02/2017.
 */

public class MoviePreferences {
    public static final String API_KEY = "94d3070c8f98a4612d323a6fa918b5a2";
    public static final String sort_popular = "popular";
    public static final String sort_rated = "top_rated";
    public static boolean sortByPopularity = true;

    public static boolean isSortByPopularity() {
        return sortByPopularity;
    }
}
