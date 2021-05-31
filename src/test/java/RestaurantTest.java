import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestaurantTest {

    LocalTime openingTime = LocalTime.parse("10:30:00");
    LocalTime closingTime = LocalTime.parse("22:00:00");

    @Spy
    Restaurant restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    //REFACTOR ALL THE REPEATED LINES OF CODE

    @BeforeAll
    public void setUp(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){

        Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("14:00:00"));

        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){

        Mockito.when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("09:00:00"));

        assertFalse(restaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ITEM TOTAL<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void return_total_order_value_for_selected_items() {

        List<String> itemsList = new ArrayList<>();
        itemsList.add("Sweet corn soup");
        itemsList.add("Vegetable lasagne");

        int orderTotal = restaurant.getOrderTotal(itemsList);

        assertEquals(388, orderTotal);
    }

    @Test
    public void return_total_order_value_for_no_selected_items() {

        List<String> itemsList = new ArrayList<>();

        int orderTotal = restaurant.getOrderTotal(itemsList);

        assertEquals(0, orderTotal);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ITEM TOTAL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}