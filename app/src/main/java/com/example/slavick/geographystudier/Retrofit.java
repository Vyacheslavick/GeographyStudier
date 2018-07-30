package com.example.slavick.geographystudier;

        import java.util.List;
        import retrofit.Callback;
        import retrofit.RestAdapter;
        import retrofit.http.GET;
        import retrofit.http.Path;
        import retrofit.http.Query;

public class Retrofit {

    private static final String ENDPOINT = "https://restcountries.eu/rest";
    private static ApiInterface apiInterface;
    static {
        initialize();
    }

    interface ApiInterface {
        @GET("/v2/region/{region}")
        void getCountries(@Path("region") String countryName,
                          @Query("fullText") String query,
                          Callback<List<Country>> callback);
        @GET("/v2/all")
        void getAll(Callback<List<Country>> callback);
    }
    public static void initialize(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        apiInterface = restAdapter.create(ApiInterface.class);
    }
    public static void getCountries(String name, Callback<List<Country>> callback){
        apiInterface.getCountries(name,"true", callback);
    }
    public static void getAll(Callback<List<Country>> callback){
        apiInterface.getAll(callback);
    }
}