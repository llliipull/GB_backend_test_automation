package HW5;

import HW5.Data.AddToShoppingListRequest;
import HW5.Data.AddToShoppingListResult;
import HW5.Data.DeleteFromShoppingListResult;
import HW5.Data.GetShoppingListResult;
import retrofit2.Call;
import retrofit2.http.*;

public interface SpoonacularApi {

    @POST("/mealplanner/gbhwuser/shopping-list/items")
    Call<AddToShoppingListResult> addToShoppingListResult
            (@Body AddToShoppingListRequest request, @Query("apiKey") String apiKey, @Query("hash") String hash);

    @GET("/mealplanner/gbhwuser/shopping-list")
    Call<GetShoppingListResult> getShoppingListResult (@Query("apiKey") String apikey,
                                                       @Query("hash") String hash);

    @DELETE("/mealplanner/gbhwuser/shopping-list/items/{id}")
    Call<DeleteFromShoppingListResult> deleteFromShoppingListResult (@Path("id") Integer id,
    @Query("apiKey") String apikey,
    @Query("hash") String hash);




}
