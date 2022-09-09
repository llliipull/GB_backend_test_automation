package HW5.Data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToShoppingListRequest {

    private String item;
    private String aisle;
    private Boolean parse;
}
