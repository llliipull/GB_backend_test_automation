package HW5;

import HW5.Data.AddToShoppingListRequest;
import HW5.Data.AddToShoppingListResult;
import HW5.Data.DeleteFromShoppingListResult;
import HW5.Data.GetShoppingListResult;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpoonacularService {

    private SpoonacularApi api;
    private static final String API_KEY = "d64cb88ee7fa4dd98c3cead1d89b25b9";
    private static final String HASH = "74a219d083326916b34159c089033d8269f020ab";

    public SpoonacularService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(SpoonacularApi.class);
    }


    public AddToShoppingListResult addToShoppingListResult(AddToShoppingListRequest request){
        Call<AddToShoppingListResult> call = api.addToShoppingListResult(request,API_KEY, HASH);
        return RetrofitUtils.execute(call);
    }

    public GetShoppingListResult getShoppingListResult() {
        Call<GetShoppingListResult> call = api.getShoppingListResult(API_KEY, HASH);
        return RetrofitUtils.execute(call);
    }

    public DeleteFromShoppingListResult deleteFromShoppingListResult() {
        Call<DeleteFromShoppingListResult> call = api.deleteFromShoppingListResult
                (getShoppingListResult().getAisles().get(0).getItems().get(0).getId(),API_KEY, HASH);
        return RetrofitUtils.execute(call);
    }
}
