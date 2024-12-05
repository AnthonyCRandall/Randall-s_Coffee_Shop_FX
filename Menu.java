

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Toggle;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.CheckBox;

/**
 * This JavaFX application will simulate a coffee shop app.
 *
 * @author Randall
 * @version 0.1
 */
public class Menu extends Application
{
    // Global section
    private StackPane myStackPane; 
    private String selectedDrink;
    private String selectedSize;
    private ToggleGroup drinkGroup;   
    private ToggleGroup drinkSize;
    private ChoiceBox<String> milkChoice;
    private Label sizeLabel = new Label("Size Options");
    private Separator separator = new Separator();
    private Separator separator1 = new Separator();
    private Label included = new Label("What's Included");
    private CheckBox scone = new CheckBox("Scone");
    private CheckBox croissant = new CheckBox("Croissant");
    private CheckBox bcm = new CheckBox("Banana Chip Muffin");
    
    @Override
    public void start(Stage stage)
    {
        myStackPane = new StackPane(); // Lets create a stackpane to hold the different pages of the Randalls cafe application
        drinkSize = new ToggleGroup(); // We want to only declare this ToggleGroup once so that the user cant select a size for multiple drinks across the entire program
        
        // Lets start creating the pages for this app
        GridPane landingPage = createLandingPage();
        GridPane drinkListPage = createDrinkListPage();
        GridPane customizationPage = createCustomizationPage();
        GridPane checkoutPage = createCheckoutPage(drinkGroup);
        GridPane hotCoffeePage = createHotCoffeePage();
        GridPane icedCoffeePage = createIcedCoffeePage();
        GridPane blendedBeveragesPage = createBlendedBeveragesPage();
        GridPane icedTeaPage = createIcedTeaPage();
        GridPane hotTeaPage = createHotTeaPage();
        
        // Now that we have those pages created, lets store them in the stackpane
        myStackPane.getChildren().addAll(landingPage, drinkListPage, customizationPage, checkoutPage, hotCoffeePage, icedCoffeePage,
                                         blendedBeveragesPage, icedTeaPage, hotTeaPage);
        
        // We want to only make the inital page visable in our window when the app is first ran
        landingPage.setVisible(true);
        drinkListPage.setVisible(false);
        customizationPage.setVisible(false);
        checkoutPage.setVisible(false);
        hotCoffeePage.setVisible(false);
        icedCoffeePage.setVisible(false);
        blendedBeveragesPage.setVisible(false);
        icedTeaPage.setVisible(false);
        hotTeaPage.setVisible(false);

        // JavaFX must have a Scene (window content) inside a Stage (window)
        Scene scene = new Scene(myStackPane, 500,600); // This is the size of the original window once program is ran
        stage.setTitle("Randalls Coffee Shop Application");
        stage.setScene(scene);
        stage.show();
        
        // Lets make this thing look pretty and link our external stylesheet we made
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        // I put my stylesheet in the same folder as this project so there was no path needed
    }    
        
    // Lets create this beautiful landing page to Randalls application
    private GridPane createLandingPage(){
        // Sets the gridpane for our UI for the landingPage
        GridPane landingPage = new GridPane();
        landingPage.setPadding(new Insets(10, 10, 10, 10));
        landingPage.setMinSize(300, 300);
        landingPage.setHgap(10);
        landingPage.setVgap(10);
        
        // Lets set the background to a cool gradient
        landingPage.getStyleClass().add("landingPage-gradient"); // We created this class in our CSS file
        
        // Lets now instatiate the elements that we would like to be on this page
        Button getStarted = new Button("Get Started");
        
        // Lets make a method that "takes us" to the drinkListPage when the button is clicked
        getStarted.setOnAction(this::goToDrinkList);
        // The "" are there because essentially we are hiding all the other pages and just making the drinkListPage visible
        
        // Lets actually place the getStarted button on the gridpane
        landingPage.add(getStarted, 2, 50);
        
        // Return the landingPage where it is then assigned to landingPage
        return landingPage;
    }
    
    
    // Lets create the page of all of the drinks Randalls cafe has to offer
    private GridPane createDrinkListPage(){
        // Sets the gridpane for our UI for the drinkListPage
        GridPane drinkListPage = new GridPane();
        drinkListPage.setPadding(new Insets(10, 10, 10, 10));
        drinkListPage.setMinSize(300, 300);
        drinkListPage.setHgap(10);
        drinkListPage.setVgap(10);
        
        // Instantiates the objects we want on the drinkListPage
        Label drinkTitle = new Label("Order");
        Label result = new Label("");
        
        // Lets make radio buttons to select the type of drink
        RadioButton hotCoffee = new RadioButton("Hot Coffee   + $3.50");
        RadioButton icedCoffee = new RadioButton("Iced Coffee   + $4.00");
        RadioButton blendedBev = new RadioButton("Blended Beverages   + $5.50");
        RadioButton icedTea = new RadioButton("Iced Tea   + $3.95");
        RadioButton hotTea = new RadioButton("Hot Tea   + $3.75");
        
        // Lets toggle these radio buttons so only ONE can be selected at a time
        drinkGroup = new ToggleGroup();
        
        // Now we must not forget to add these radio buttons to the drinkGroup because its empty right now
        hotCoffee.setToggleGroup(drinkGroup);
        icedCoffee.setToggleGroup(drinkGroup);
        blendedBev.setToggleGroup(drinkGroup);
        icedTea.setToggleGroup(drinkGroup);
        hotTea.setToggleGroup(drinkGroup);
        
        // Lets create a vbox to align all the radio buttons up nicely
        VBox drinkGroupVBox = new VBox(10);
        drinkGroupVBox.getChildren().addAll(hotCoffee, icedCoffee, blendedBev, icedTea, hotTea);
        
        // Lets create a button that will take us to the checkout page
        Button confirmButton = new Button("Customize");
        
        // Set actions for our buttons on this page
        confirmButton.setOnAction(event -> goToCustomization(event, drinkGroup));
        
        // Lets start d-e-s-i-g-n-ing it by adding our elements to the pane
        drinkListPage.add(confirmButton, 10, 20);
        drinkListPage.add(drinkGroupVBox, 0, 5);
        
        // Returns the drinkListPage GridPane to be set the visible
        return drinkListPage;
    }
    
    
    // Lets create the page for the customization of drinks
    private GridPane createCustomizationPage(){
        // Sets the gridpane for our UI for the customizationPage
        GridPane customizationPage = new GridPane();
        customizationPage.setPadding(new Insets(10, 10, 10, 10));
        customizationPage.setMinSize(300, 300);
        customizationPage.setHgap(10);
        customizationPage.setVgap(10);
        
        Label customizationTitle = new Label("Lets Start Customizing");
        Button checkoutButton = new Button("Go to Checkout");
        Button drinkListButton = new Button("Back to Drink List");
        
        checkoutButton.setOnAction(this::goToCheckout);     
        drinkListButton.setOnAction(this::goToDrinkList);  
        
        customizationPage.add(customizationTitle, 0, 0);
        customizationPage.add(checkoutButton, 10, 10);
        customizationPage.add(drinkListButton, 0, 10);
        
        return customizationPage;
    }
    
    // Finally, lets create the page for checkout
    public GridPane createCheckoutPage(ToggleGroup drinkGroup){
        // Sets the gridpane for our UI for the customizationPage
        GridPane checkoutPage = new GridPane();
        checkoutPage.setPadding(new Insets(10, 10, 10, 10));
        checkoutPage.setMinSize(300, 300);
        checkoutPage.setHgap(10);
        checkoutPage.setVgap(10);
        
        Label checkoutTitle = new Label("Checkout");
        Button toCustomization = new Button("Back to Customization");
        Button calculateOrder = new Button("Confirm Purchase");
        Label totalLabel = new Label("Your total is: ");
        
        toCustomization.setOnAction(event -> goToCustomization(event, drinkGroup));
        calculateOrder.setOnAction(event -> calculateTotal(event, drinkGroup, drinkSize, totalLabel, milkChoice, scone, croissant, bcm));
        
        checkoutPage.add(toCustomization, 0, 10);
        checkoutPage.add(calculateOrder, 10, 10);
        checkoutPage.add(totalLabel, 11, 10);
        
        return checkoutPage;
    }
    
    // The following methods will be used to create the GridPanes for the different drinks
    // I want a different Gridpane to show up depending on which RadioButton the user selects 
    //  - Hot Coffee
    //  - Iced Coffee
    //  - Blended Beverages
    //  - Iced Tea
    //  - Hot Tea
    
    //createHotCoffeePage() - creates the Gridpane for the "Hot Coffee" selection and returns it
    private GridPane createHotCoffeePage(){
        // Sets the gridpane for our UI for the customizationPage
        GridPane hotCoffeePage = new GridPane();
        hotCoffeePage.setPadding(new Insets(10, 10, 10, 10));
        hotCoffeePage.setMinSize(300, 300);
        hotCoffeePage.setHgap(10);
        hotCoffeePage.setVgap(10);
        
        // Instantiate the objects for the pane
        Label hotCoffeeLabel = new Label("Hot Coffee");
        Button backToDrinkList = new Button("Back to Home");
        Button toCheckoutPage = new Button("To Checkout");
        Label calories = new Label("15 calories");
        Label sizeOptions = new Label("Size options");
        
        // Create RadioButtons to represent the sizes available for the drink
        RadioButton small = new RadioButton("Small   + $1.50");
        RadioButton medium = new RadioButton("Medium   + $2.50");
        RadioButton large = new RadioButton("Large   + $3.50");
        
        // Assign the Radio Buttons to the drinkSize ToggleGroup
        small.setToggleGroup(drinkSize);
        medium.setToggleGroup(drinkSize);
        large.setToggleGroup(drinkSize);
        
        // Create an Hbox to align the Radio Buttons horizontally 
        HBox sizeHbox = new HBox(40);
        sizeHbox.getChildren().addAll(small, medium, large);
        
        // Set an action for the buttons 
        backToDrinkList.setOnAction(this::goToDrinkList);
        toCheckoutPage.setOnAction(this::goToCheckout);
        
        // Position the objects on the pane
        hotCoffeePage.add(hotCoffeeLabel, 0, 0);
        hotCoffeePage.add(backToDrinkList, 0, 10);
        hotCoffeePage.add(toCheckoutPage, 10, 10);
        hotCoffeePage.add(sizeHbox, 5, 22);
        
        // Return the GridPane
        return hotCoffeePage;
    }           
    
    //createIcedCoffeePage() - creates the Gridpane for the "Iced Coffee" selection and returns it
    private GridPane createIcedCoffeePage(){
        // Sets the gridpane for our UI for the customizationPage
        GridPane icedCoffeePage = new GridPane();
        icedCoffeePage.setPadding(new Insets(10, 10, 10, 10));
        icedCoffeePage.setMinSize(300, 300);
        icedCoffeePage.setHgap(10);
        icedCoffeePage.setVgap(10);
        
        // Instantiate the objects for the pane
        Label icedCoffeeLabel = new Label("Iced Coffee");
        Button backToDrinkList = new Button("Back to Home");
        Button toCheckoutPage = new Button("To Checkout");
        Label iCoffeeCalories = new Label("30 calories");
        
        // Create RadioButtons to represent the sizes available for the drink
        RadioButton small = new RadioButton("Small   + $1.50");
        RadioButton medium = new RadioButton("Medium   + $2.50");
        RadioButton large = new RadioButton("Large   + $3.50");
        
        // Assign the Radio Buttons to the drinkSize ToggleGroup
        small.setToggleGroup(drinkSize);
        medium.setToggleGroup(drinkSize);
        large.setToggleGroup(drinkSize);
        
        // Create an Hbox to align the Radio Buttons horizontally 
        HBox sizeHbox = new HBox(40);
        sizeHbox.getChildren().addAll(small, medium, large);
        
        // Set an action for the buttons
        backToDrinkList.setOnAction(this::goToDrinkList);
        toCheckoutPage.setOnAction(this::goToCheckout);
        
        // Position the objects on the pane
        icedCoffeePage.add(icedCoffeeLabel, 2, 10);
        icedCoffeePage.add(backToDrinkList, 0, 20);
        icedCoffeePage.add(toCheckoutPage, 8, 20);
        icedCoffeePage.add(sizeHbox,3, 11);
        icedCoffeePage.add(iCoffeeCalories, 2, 11);
        
        // Return the GridPane
        return icedCoffeePage;
    }      
    
    //createBlendedBeveragesPage() - creates the Gridpane for the "Blended Beverages" selection and returns it    
    public GridPane createBlendedBeveragesPage(){
        // Sets the gridpane for our UI for the customizationPage
        GridPane blendedBeveragesPage = new GridPane();
        blendedBeveragesPage.setPadding(new Insets(10, 10, 10, 10));
        blendedBeveragesPage.setMinSize(300, 300);
        blendedBeveragesPage.setHgap(5);
        blendedBeveragesPage.setVgap(5);
        
        // Add the background color
        blendedBeveragesPage.getStyleClass().add("gridpane-background");
        
        // Instantiate the objects for the pane
        Label blendedBeveragesLabel = new Label("Blended Beverages");
        Button backToDrinkList = new Button("<");
        Button toCheckoutPage = new Button(">");
        Label addOnLabel = new Label("Add On?");
        
        // A few things a user can add to their order before checking out
        //Scone, Croissant, Banana Chip Muffin 
        HBox extrasHBox = new HBox(50);
        extrasHBox.getChildren().addAll(scone, croissant, bcm);
        
        // Lets load an image to show what it looks like
        Image blendedBeveragePng = new Image("file:images/blendedBeverage.jpg");
        ImageView blendedBeverageIV = new ImageView(blendedBeveragePng);
        blendedBeverageIV.setFitWidth(350);
        blendedBeverageIV.setFitHeight(350);
        blendedBeverageIV.setPreserveRatio(true);
        
        // Create RadioButtons to represent the sizes available for the drink
        RadioButton small = new RadioButton("Small   + $1.50");
        RadioButton medium = new RadioButton("Medium   + $2.50");
        RadioButton large = new RadioButton("Large   + $3.50");
        
        // Assign the Radio Buttons to the drinkSize ToggleGroup
        small.setToggleGroup(drinkSize);
        medium.setToggleGroup(drinkSize);
        large.setToggleGroup(drinkSize);
        
        // Create an Hbox to align the Radio Buttons horizontally 
        HBox sizeHbox = new HBox(80);
        sizeHbox.getChildren().addAll(small, medium, large);
        GridPane.setColumnSpan(sizeHbox, 20);
        
        // Create a ChoiceBox to have a user select things such as type of milk 
        milkChoice = new ChoiceBox<>();
        String[] milk = {"--- Please Select One ---", "2% Milk", "Almond", "Half & Half", "Coconut", "Nonfat Milk", "Oatmilk", "Soy", "Whole Milk"}; // Holding the available types of milk
        
        milkChoice.getItems().addAll(milk); // This will add every milk element into our choicebox

        // Set an action for our objects
        backToDrinkList.setOnAction(this::goToDrinkList);
        toCheckoutPage.setOnAction(this::goToCheckout);
        milkChoice.setOnAction(event -> selectedMilk(event, milkChoice));
        
        // Position the objects on the pane
        blendedBeveragesPage.add(blendedBeveragesLabel, 0, 0);
        blendedBeveragesPage.add(backToDrinkList, 0, 36);
        blendedBeveragesPage.add(toCheckoutPage, 18, 36);
        blendedBeveragesPage.add(sizeHbox, 0, 16);
        blendedBeveragesPage.add(milkChoice, 0, 27);
        blendedBeveragesPage.add(sizeLabel, 0, 10);
        blendedBeveragesPage.add(separator, 0, 12);
        blendedBeveragesPage.add(included, 0, 22);
        blendedBeveragesPage.add(separator1, 0, 24);
        GridPane.setColumnSpan(separator1, 2);
        blendedBeveragesPage.add(blendedBeverageIV, 0, 4);
        blendedBeveragesPage.add(extrasHBox, 0, 31);
        
        // Return the GridPane
        return blendedBeveragesPage;
    }     
    
    //createIcedTeaPage() - creates the Gridpane for the "Iced Tea" selection and returns it    
    private GridPane createIcedTeaPage(){
        // Sets the gridpane for our UI for the customizationPage
        GridPane icedTeaPage = new GridPane();
        icedTeaPage.setPadding(new Insets(10, 10, 10, 10));
        icedTeaPage.setMinSize(300, 300);
        icedTeaPage.setHgap(10);
        icedTeaPage.setVgap(10);
        
        // Instantiate the objects for the pane
        Label icedTeaLabel = new Label("Iced Tea");
        Button backToDrinkList = new Button("Back to Home");
        Button toCheckoutPage = new Button("To Checkout");
        
        // Create RadioButtons to represent the sizes available for the drink
        RadioButton small = new RadioButton("Small   + $1.50");
        RadioButton medium = new RadioButton("Medium   + $2.50");
        RadioButton large = new RadioButton("Large   + $3.50");
        
        // Assign the Radio Buttons to the drinkSize ToggleGroup
        small.setToggleGroup(drinkSize);
        medium.setToggleGroup(drinkSize);
        large.setToggleGroup(drinkSize);
        
        // Create an Hbox to align the Radio Buttons horizontally 
        HBox sizeHbox = new HBox(40);
        sizeHbox.getChildren().addAll(small, medium, large);
        
        // Set an action for the buttons
        backToDrinkList.setOnAction(this::goToDrinkList);
        toCheckoutPage.setOnAction(this::goToCheckout);
        
        // Position the objects on the pane
        icedTeaPage.add(icedTeaLabel, 0, 0);
        icedTeaPage.add(backToDrinkList, 0, 10);
        icedTeaPage.add(toCheckoutPage, 10, 10);
        icedTeaPage.add(sizeHbox, 5, 22);
        
        // Return the GridPane
        return icedTeaPage;
    }      
    
    //createHotTeaPage() - creates the Gridpane for the "Hot Tea" selection and returns it    
    private GridPane createHotTeaPage(){
        // Sets the gridpane for our UI for the customizationPage
        GridPane hotTeaPage = new GridPane();
        hotTeaPage.setPadding(new Insets(10, 10, 10, 10));
        hotTeaPage.setMinSize(300, 300);
        hotTeaPage.setHgap(10);
        hotTeaPage.setVgap(10);
        
        // Instantiate the objects for the pane
        Label hotTeaLabel = new Label("Hot Tea");
        Button backToDrinkList = new Button("Back to Home");
        Button toCheckoutPage = new Button("To Checkout");
        
        // Create RadioButtons to represent the sizes available for the drink
        RadioButton small = new RadioButton("Small   + $1.50");
        RadioButton medium = new RadioButton("Medium   + $2.50");
        RadioButton large = new RadioButton("Large   + $3.50");
        
        // Assign the Radio Buttons to the drinkSize ToggleGroup
        small.setToggleGroup(drinkSize);
        medium.setToggleGroup(drinkSize);
        large.setToggleGroup(drinkSize);
        
        // Create an Hbox to align the Radio Buttons horizontally 
        HBox sizeHbox = new HBox(40);
        sizeHbox.getChildren().addAll(small, medium, large);
        
        // Set an action for the buttons
        backToDrinkList.setOnAction(this::goToDrinkList);
        toCheckoutPage.setOnAction(this::goToCheckout);
    
        // Position the objects on the pane
        hotTeaPage.add(hotTeaLabel, 0, 0);
        hotTeaPage.add(backToDrinkList, 0, 10);
        hotTeaPage.add(toCheckoutPage, 10, 10);
        hotTeaPage.add(sizeHbox, 5, 22);
        
        // Return the GridPane
        return hotTeaPage;
    }     
    
    // goToCustomization() - checks which Radio Button (if any) were selected then sets that GridPanes visibility to true and the rest to false
    private void goToCustomization(ActionEvent event, ToggleGroup drinkGroup){
        RadioButton drink = (RadioButton) drinkGroup.getSelectedToggle();
        if(drink != null){
            if(drink.getText().equals("Hot Coffee   + $3.50")){
                showPage("hotCoffee");
            }
            else if(drink.getText().equals("Iced Coffee   + $4.00")){
                showPage("icedCoffee");
            }
            else if(drink.getText().equals("Blended Beverages   + $5.50")){
                showPage("blendedBeverages");
            }
            else if(drink.getText().equals("Iced Tea   + $3.95")){
                showPage("icedTea");
            }
            else{
                showPage("hotTea");
            }
        }
        else{   // If the user tries and moves onto customization before selecting a Radio Button, alert them to choose one
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION, "You must select a drink before continuing on to customization");
            noSelection.showAndWait();
        }
        
    }
    
    // goToCheckout() - goes to the checkout page
    private void goToCheckout(ActionEvent event){
        RadioButton size = (RadioButton) drinkSize.getSelectedToggle();
        if(size != null && milkChoice != null){
            showPage("checkout");
        }
        else{   // If the user tries and moves onto customization before selecting a Radio Button, alert them to choose one
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION, "You must select a size before continuing on to checkout");
            noSelection.showAndWait();
        }        
    }
    
    // goToDrinkList() - goes to the drinkList page
    private void goToDrinkList(ActionEvent event){
        milkChoice.setValue(null);
        showPage("drinkList");
    }    
    
    private void goToLandingPage(ActionEvent event){
        showPage("landing");
    }
    
    private void calculateTotal(ActionEvent event, ToggleGroup drinkGroup, ToggleGroup drinkSize, Label totalLabel, ChoiceBox<String> milkChoice, CheckBox scone, CheckBox croissant, CheckBox bcm){
        
        double total = 0; // Without any selections we will start at $0.00
        
        double selectedDrink = selectedDrink(event, drinkGroup);
        double selectedSize = selectedDrinkSize(event, drinkSize);
        double selectedMilk = selectedMilk(event, milkChoice);
        double selectedExtras = getExtras(event, scone, croissant, bcm);
        
        // Lets create an array to hold all of the customizations
        double[] selections = {selectedDrink, selectedSize, selectedMilk, selectedExtras};
        
        // Now lets loop through the array to get the final total
        for(double selection : selections){
            total += selection;
        }
        
        double tip = total * .08675; // Lets add a 8.675% tip to the toal (my workers deserve it)
        total = total + tip;
        
        String totalToFormat = String.format("$%,.2f", total); // Take the cummulative total and format it to look like USD
        
        totalLabel.setText(totalToFormat); // Assign the formatted string to the total label on the checkout page 
    }
    
    // Now...lets create the method to cycle through the pages
    private void showPage(String pageName){
        for(int i = 0; i < myStackPane.getChildren().size(); i++){
            myStackPane.getChildren().get(i).setVisible(false);
        }
        // This switch will check to see what page we are going to after a navigation button was clicked, and will only display that page
        switch (pageName){
            case "landing":
                myStackPane.getChildren().get(0).setVisible(true);
                break;
            case "drinkList":
                myStackPane.getChildren().get(1).setVisible(true);
                break;
            case "customization":
                myStackPane.getChildren().get(2).setVisible(true);
                break;                
            case "checkout":
                myStackPane.getChildren().get(3).setVisible(true);
                break; 
            case "hotCoffee":
                myStackPane.getChildren().get(4).setVisible(true);
                break;
            case "icedCoffee":
                myStackPane.getChildren().get(5).setVisible(true);
                break;
            case "blendedBeverages":
                myStackPane.getChildren().get(6).setVisible(true);
                break;
            case "icedTea":
                myStackPane.getChildren().get(7).setVisible(true);
                break;
            case "hotTea":
                myStackPane.getChildren().get(8).setVisible(true);
                break;
        }  
    }    
    
    public double selectedDrink(ActionEvent event, ToggleGroup drinkGroup){
        // Now lets do the same for the type of drink
        RadioButton drink = (RadioButton) drinkGroup.getSelectedToggle();
        
        if(drink.getText().equals("Hot Coffee   + $3.50")){
            return 3.50;
        }
        else if(drink.getText().equals("Iced Coffee   + $4.00")){
            return 4.00;
        }
        else if(drink.getText().equals("Blended Beverages   + $5.50")){
            return 5.50;
        }
        else if(drink.getText().equals("Hot Tea   + $3.75")){
            return 3.75;
        }
        else{
            return 3.95;
        }        
    }
    
    // selectedDrinkSize() - takes in the drinkSize ToggleGroup, gets the selected toggle, and returns the cost of the size
    public double selectedDrinkSize(ActionEvent event, ToggleGroup drinkSize){
        // Lets get the selected size of the drink and add that cost to the total
        RadioButton size = (RadioButton) drinkSize.getSelectedToggle();
        
        if(size.getText().equals("Small   + $1.50")){
            return 1.50;
        } else if(size.getText().equals("Medium   + $2.50")){
            return 2.50;
        }
        else{
            return 3.50;
        }        
    }
    
    // selectedMilk() - takes in the ChoiceBox, gets its value, then returns how much it costs
    public double selectedMilk(ActionEvent event, ChoiceBox<String> milkChoice){
        String milk = milkChoice.getValue();
        if(milk == null){
            return 0.00;    
        }
        else if(milk.equals("--- Please Select One ---")){
            Alert noSelection = new Alert(Alert.AlertType.INFORMATION, "You must select a milk");
            noSelection.showAndWait();
            return 0.0;                
        }
        else if(milk.equals("Oatmilk") || milk.equals("Soy") || milk.equals("Almond")){
             return 0.50;
        }
        else if(milk.equals("Coconut")){
            return 1.00;
        }
        else if(milk.equals("2% Milk") || milk.equals("Half & Half") || milk.equals("Nonfat Milk") || milk.equals("Whole Milk")){
            return 0.25;
        }
        return 0.00;
    }

    // getExtras() - returns a total for whichever danishes were checked
    public double getExtras(ActionEvent event, CheckBox scone, CheckBox croissant, CheckBox bcm){
        double total = 0;
        
        if(scone.isSelected()){
            total += 3.50;    
        }
        if(croissant.isSelected()){
            total += 3.00;
        }
        if(bcm.isSelected()){
            total += 4.50;
        }
        
        return total;
    }
        
}    
