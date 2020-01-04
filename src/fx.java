//import com.sun.xml.internal.bind.v2.runtime.property.ValueProperty;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Math.PI;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;


public class fx extends Application{

    private int placescount;
    private int stopscount;

    double RADIAN_FACTOR = 180 / PI;
    int EARTH_RADIUS = 6371;
    int totalMiles = 0;

    String name;

    TripWriter trip = new TripWriter();


    public static void main(String[] args) {
        launch(args);
    }

    public int totalMiles(int lat1, int lat2, int long1, int long2)
    {
        double x = (Math.sin(lat1/RADIAN_FACTOR) * Math.sin(lat2/RADIAN_FACTOR))
                + (Math.cos(lat1/RADIAN_FACTOR)
                * Math.cos(lat2/RADIAN_FACTOR)
                * Math.cos((long2/RADIAN_FACTOR) - (long1/RADIAN_FACTOR)));
        double distance = EARTH_RADIUS * Math.atan((Math.sqrt(1 - Math.pow(x, 2))/x));
        return (int)Math.round(distance);
    }

    private ArrayList<Trip> placesssList(){
        trip.initTripArrayList();
        return trip.trips;
    }

    private ArrayList<String> placesList(){
        trip.addTrips(placesssList());
        return trip.textOnly;
    }

    private ArrayList<String> stopsList() {
        ArrayList<String> dude = new ArrayList<>();
        return dude;
    }

    private ArrayList<Trip> stopsss = new ArrayList<>();



    //check if all text fields are filled
    public boolean filledFields(String city, String state, String latd, String latm, String longd, String longm) {
        if (city.isEmpty() || state.isEmpty() || latd.isEmpty() || latm.isEmpty() || longd.isEmpty()
                || longm.isEmpty()){
            return false;
        }
        return true;
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Trip Planner - " + name);
        Button newButt = new Button("New");
        Button saveButt = new Button("Save");
        Button loadButt = new Button("Load");
        Button update = new Button("Update");
        Button plusPossible = new Button("+");
        Button minusPossible = new Button("-");
        Button plusTrip = new Button("+");
        Button minusTrip = new Button("-");
        newButt.setPrefWidth(100);
        saveButt.setPrefWidth(100);
        loadButt.setPrefWidth(100);
        update.setPrefWidth(100);
        plusPossible.setPrefWidth(50);
        minusPossible.setPrefWidth(50);
        plusTrip.setPrefWidth(50);
        minusTrip.setPrefWidth(50);



        //LABELS
        Label possibleStops = new Label("Possible Stops:");
        Label tripStops = new Label("Trip Stops:");
        final Label miles = new Label("Total Mileage: " + totalMiles);
        Label city = new Label("City:");
        Label state = new Label("State:");
        Label latDeg = new Label("Latitude Degrees:");
        Label latMin = new Label("Latitude Minutes:");
        Label longDeg = new Label("Longitude Degrees:");
        Label longMin = new Label("Longitude Minutes:");


        //TEXT FIELDS
        TextField citee = new TextField();
        citee.setMaxWidth(120); //width of textbox
        TextField states = new TextField();
        states.setMaxWidth(120);
        TextField latD = new TextField();
        latD.setMaxWidth(120);
        TextField latM = new TextField();
        latM.setMaxWidth(120);
        TextField longD = new TextField();
        longD.setMaxWidth(120);
        TextField longM = new TextField();
        longM.setMaxWidth(120);



        //POSSIBLE TRIPS LIST
        ListView<String> places = new ListView <>();
        places.setOrientation(Orientation.VERTICAL);
        places.setPrefSize(200,300);
        places.getItems().addAll(placesList());



        //If a place was clicked on
        places.addEventFilter(MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            //Toggle place selection
            @Override
            public void handle(MouseEvent mouseEvent) {
                placescount++;
                int i = places.getSelectionModel().getSelectedIndex();
                if (places.getSelectionModel().isSelected(i) && placescount%2==0) {
                    places.getSelectionModel().clearSelection(i);

                }

                //show info in textfield
                if (places.getSelectionModel().isSelected(places.getSelectionModel().getSelectedIndex())) {

                    int index = places.getSelectionModel().getSelectedIndex();

                    citee.insertText(0, placesssList().get(index).getName());//oh no
                    states.insertText(0, placesssList().get(index).getState());
                    latD.insertText(0, Integer.toString(placesssList().get(index).getLatDeg()));
                    latM.insertText(0, Integer.toString(placesssList().get(index).getLatMin()));
                    longD.insertText(0, Integer.toString(placesssList().get(index).getLongDeg()));
                    longM.insertText(0, Integer.toString(placesssList().get(index).getLongMin()));
                    //System.out.println(places.getSelectionModel().getSelectedIndex());
                }
                else{
                    citee.clear(); states.clear(); latD.clear(); latM.clear(); longD.clear(); longM.clear();
                }

            }
        });


        //Trip Stops List
        ListView<String> stops = new ListView <String>();
        stops.setOrientation(Orientation.VERTICAL);
        stops.setPrefSize(200,300);
        stops.getItems().addAll(stopsList());



        //todo
        stops.addEventFilter(MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stopscount++;
                if (stops.getSelectionModel().isSelected(stops.getSelectionModel().getSelectedIndex()) && stopscount%2==0) {
                    stops.getSelectionModel().clearSelection(stops.getSelectionModel().getSelectedIndex());

                }
            }
        });



        //NEW BUTTON ACTION
        newButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog newbutton = new TextInputDialog();
                newbutton.setTitle("Create a New Trip");
                newbutton.setHeaderText(null);
                newbutton.setContentText("What is the name of your new trip?");
                Optional<String> result = newbutton.showAndWait();
                if (result.isPresent()){
                    name = result.get();
                    primaryStage.setTitle("Trip Planner - " + name);
                    stops.getItems().clear();

                }
            }
        });

        //SAVE BUTTON ACTION TODO

        saveButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser filechooser = new FileChooser();
                File saveFile = filechooser.showSaveDialog(primaryStage);
                filechooser.setTitle("Save Your Trip");
                filechooser.setInitialFileName(primaryStage.getTitle());

                if(saveFile != null){
                  //save the trip stops list and possible trips list
                    trip.writeArrayListToFile(saveFile, placesList());

                }



                System.out.println("Hello World!"); //saves trip on a file using file chooser
            }
        });


        //LOAD BUTTON ACTION TODO
        loadButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser filechooser = new FileChooser();
                File selectFile = filechooser.showOpenDialog(primaryStage);
                filechooser.setTitle("Load a Trip");

                if(selectFile != null){
                    //open trip stops file and possible stops file
                    TripReader.loadTripsFromFile(selectFile.getName()); //holy moly
                }


                System.out.println("jello");  //loads a trip from a file using file chooser
            }
        });


        //UPDATE BUTTON: UPDATES WHATEVER IS SELECTED FROM THE POSSIBLE STOPS LIST
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (places.getSelectionModel().isSelected(places.getSelectionModel().getSelectedIndex()) &&
                        filledFields(citee.getText(),states.getText(),latD.getText(),latM.getText(),longD.getText(),longM.getText())== true) {
                    int index = places.getSelectionModel().getSelectedIndex();
                    placesssList().remove(index);
                    placesssList().add(index,new Trip(citee.getText(),states.getText(),Integer.parseInt(latD.getText()),Integer.parseInt(latM.getText()),
                            Integer.parseInt(longD.getText()),Integer.parseInt(longM.getText())));
                    placesList().remove(index);
                    placesList().add(index,citee.getText() + ", " + states.getText());
                    places.getItems().remove(index);
                    places.getItems().add(index,citee.getText() + ", " + states.getText());


                    citee.clear(); states.clear(); latD.clear(); latM.clear(); longD.clear(); longM.clear(); //clear after action

                } else {
                    Alert empty = new Alert(Alert.AlertType.WARNING);
                    empty.setTitle("Uh Oh");
                    empty.setHeaderText(null);
                    empty.setContentText("Select a trip to update, and make changes in the text field(s)");
                    empty.showAndWait();

                }
            }
        });


        //POSSIBLE PLUS ACTION: ADD NEW ITEM TO POSSIBLE STOPS LIST
        plusPossible.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (filledFields(citee.getText(),states.getText(),latD.getText(),latM.getText(),longD.getText(),longM.getText()) == false) {
                    Alert empty = new Alert(Alert.AlertType.WARNING);
                    empty.setTitle("Uh Oh");
                    empty.setHeaderText(null);
                    empty.setContentText("One or more of your text fields are blank!");
                    empty.showAndWait();

                } else {

                    //trip.additionBaby(citee.getText(),states.getText(),Integer.parseInt(latD.getText()),Integer.parseInt(latM.getText()),
                    //        Integer.parseInt(longD.getText()),Integer.parseInt(longM.getText()),placesssList());

                    places.getItems().add(citee.getText() + ", " + states.getText());
                    placesList().add(citee.getText() + ", " + states.getText());

                    placesssList().add(new Trip(citee.getText(),states.getText(),Integer.parseInt(latD.getText()),Integer.parseInt(latM.getText()),
                            Integer.parseInt(longD.getText()),Integer.parseInt(longM.getText())));




                    citee.clear(); states.clear(); latD.clear(); latM.clear(); longD.clear(); longM.clear();

                    // CHANGE TO UPDATE POSSIBLE STOPS LIST: create a file that holds everything
                }

            }
        });

        //MINUS POSSIBLE ACTION: REMOVE ITEM FROM POSSIBLE STOPS LIST A N D TRIP STOPS LIST IF APPLICABLE
        minusPossible.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int selected = places.getSelectionModel().getSelectedIndex();
                places.getItems().remove(selected);

                //also remove from placesList
                placesList().remove(selected);
                System.out.println("bro");

                //and the other one
                placesssList().remove(selected);
            }

        });

        //PLUS TRIP BUTTON: ADD ITEM TO TRIP STOPS LIST FROM POSSIBLE STOPS LIST
        plusTrip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (places.getSelectionModel().isSelected(places.getSelectionModel().getSelectedIndex())){ //if something in possibleStops is selected
                    int index = places.getSelectionModel().getSelectedIndex();

                    String i = placesList().get(index);
                    stopsList().add(i); //add that to stops list

                    if (stops.getSelectionModel().isSelected(stops.getSelectionModel().getSelectedIndex())){ //if something in tripStops is selected
                        //append places.getSelectionModel() after selected tripStop
                        stops.getItems().add(stops.getSelectionModel().getSelectedIndex(), i);
                        stopsss.add(stops.getSelectionModel().getSelectedIndex(), new Trip(citee.getText(),states.getText(),Integer.parseInt(latD.getText()),Integer.parseInt(latM.getText()),
                                Integer.parseInt(longD.getText()),Integer.parseInt(longM.getText())));
                    }
                    else {
                        stops.getItems().add(i); //append it to end of list
                        stopsss.add(new Trip(citee.getText(), states.getText(), Integer.parseInt(latD.getText()), Integer.parseInt(latM.getText()),
                                Integer.parseInt(longD.getText()), Integer.parseInt(longM.getText())));


                    }


                }
                else{
                    Alert empty = new Alert(Alert.AlertType.WARNING);
                    empty.setTitle("Uh Oh");
                    empty.setHeaderText(null);
                    empty.setContentText("Select a city from Possible Stops first.");
                    empty.showAndWait();

                }

                if (stopsss.size()>1){//if there is more than one element in tripStops

                    int total = 0;
                    for (int j = 0; j < stopsss.size()-1; j++){

                        int lat1 = (stopsss.get(j).getLatDeg() + (stopsss.get(j).getLatMin()) /60);
                        int lat2 = (stopsss.get(j+1).getLatDeg() + (stopsss.get(j+1).getLatMin())/60);
                        int long1 = (stopsss.get(j).getLongDeg() + (stopsss.get(j).getLongDeg())/60);
                        int long2 = (stopsss.get(j+1).getLongDeg() + (stopsss.get(j+1).getLongDeg())/60);

                        total = totalMiles(lat1, lat2,long1,long2) + total;

                        totalMiles = total;
                    }

                }
                else{
                    totalMiles = 0;
                }


                miles.setText("Total Mileage: " + totalMiles); //UPDATE TOTAL MILES

                //System.out.println("hey");
            }
        });

        //MINUS TRIP BUTTON: REMOVE ITEM FROM TRIP STOP LIST ONLY
        minusTrip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int selected = stops.getSelectionModel().getSelectedIndex();
                stops.getItems().remove(selected);


                //stopsList().remove(selected);

                stopsss.remove(selected);



                if (stopsss.size()>1){//if there is more than one element in tripStops

                    int total = 0;
                    for (int j = 0; j < stopsss.size()-1; j++){

                        int lat1 = (stopsss.get(j).getLatDeg() + (stopsss.get(j).getLatMin()) /60);
                        int lat2 = (stopsss.get(j+1).getLatDeg() + (stopsss.get(j+1).getLatMin())/60);
                        int long1 = (stopsss.get(j).getLongDeg() + (stopsss.get(j).getLongDeg())/60);
                        int long2 = (stopsss.get(j+1).getLongDeg() + (stopsss.get(j+1).getLongDeg())/60);

                        total = totalMiles(lat1, lat2,long1,long2) + total;

                        totalMiles = total;
                    }

                }
                else{
                    totalMiles = 0;
                }

                miles.setText("Total Mileage: " + totalMiles); //CHANGE TOTAL MILES

                //System.out.println("be blank");
            }
        });



        //START

        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: #F1DDB9;");
        root.getChildren().addAll(newButt, saveButt, loadButt, citee, update, possibleStops, places, city,
                state,latDeg,latMin,longDeg,longMin,states,latD,latM,longD,longM,plusPossible,minusPossible,plusTrip,minusTrip,tripStops,miles,stops);


        //*****MAP*****
        ImageView map = new ImageView("usa_map.jpg");
        root.getChildren().add(map);
        map.setFitHeight(400);
        map.setFitWidth(700);
        GridPane.setRowIndex(map, 1);
        GridPane.setColumnIndex(map,0);
        GridPane.setRowSpan(map,4);
        GridPane.setColumnSpan(map,7);
        GridPane.setMargin(map, new Insets(10,10,0,10));




        //*****BUTTONS*****

        //new button
        GridPane.setRowIndex(newButt, 0);
        GridPane.setColumnIndex(newButt, 0);
        GridPane.setMargin(newButt, new Insets(10,10,0,10));


        //save button
        GridPane.setRowIndex(saveButt, 0);
        GridPane.setColumnIndex(saveButt, 1);
        GridPane.setMargin(saveButt, new Insets(10,10,0,10));


        //load button
        GridPane.setRowIndex(loadButt, 0);
        GridPane.setColumnIndex(loadButt, 2);
        GridPane.setMargin(loadButt, new Insets(10,10,0,10));


        //update button
        GridPane.setRowIndex(update, 12);
        GridPane.setColumnIndex(update, 5);
        GridPane.setMargin(update, new Insets(10,10,0,10));


        //plusPossible
        GridPane.setRowIndex(plusPossible, 5);
        GridPane.setColumnIndex(plusPossible, 5);
        GridPane.setMargin(plusPossible, new Insets(10,10,0,10));

        //minusPossible
        GridPane.setRowIndex(minusPossible, 5);
        GridPane.setColumnIndex(minusPossible, 6);
        GridPane.setMargin(minusPossible, new Insets(10,10,0,10));

        //plusTrip
        GridPane.setRowIndex(plusTrip, 4);
        GridPane.setColumnIndex(plusTrip, 8); //change value
        GridPane.setMargin(plusTrip, new Insets(10,10,0,10));

        //minusTrip
        GridPane.setRowIndex(minusTrip, 4);
        GridPane.setColumnIndex(minusTrip, 9); //change value
        GridPane.setMargin(minusTrip, new Insets(10,10,0,10));



        //*****TEXT FIELDS*****

        //citee field
        GridPane.setRowIndex(citee, 6);
        GridPane.setColumnIndex(citee, 6);
        GridPane.setMargin(citee, new Insets(10,10,0,10));
        //states
        GridPane.setRowIndex(states, 7);
        GridPane.setColumnIndex(states, 6);
        GridPane.setMargin(states, new Insets(10,10,0,10));
        //latDeg
        GridPane.setRowIndex(latD, 8);
        GridPane.setColumnIndex(latD, 6);
        GridPane.setMargin(latD, new Insets(10,10,0,10));
        //latMin
        GridPane.setRowIndex(latM, 9);
        GridPane.setColumnIndex(latM, 6);
        GridPane.setMargin(latM, new Insets(10,10,0,10));
        //longDeg
        GridPane.setRowIndex(longD, 10);
        GridPane.setColumnIndex(longD, 6);
        GridPane.setMargin(longD, new Insets(10,10,0,10));
        //longMin
        GridPane.setRowIndex(longM, 11);
        GridPane.setColumnIndex(longM, 6);
        GridPane.setMargin(longM, new Insets(10,10,0,10));


        //*****LABELS*****
        //possible stops label
        GridPane.setRowIndex(possibleStops, 5);
        GridPane.setColumnIndex(possibleStops, 0);
        GridPane.setMargin(possibleStops, new Insets(10,10,10,10));
        //city label
        GridPane.setRowIndex(city, 6);
        GridPane.setColumnIndex(city, 5);
        GridPane.setMargin(city, new Insets(10,10,0,10));
        //state label
        GridPane.setRowIndex(state, 7);
        GridPane.setColumnIndex(state, 5);
        GridPane.setMargin(state, new Insets(10,10,0,10));
        //latDeg
        GridPane.setRowIndex(latDeg, 8);
        GridPane.setColumnIndex(latDeg, 5);
        GridPane.setMargin(latDeg, new Insets(10,10,0,10));
        //latMin
        GridPane.setRowIndex(latMin, 9);
        GridPane.setColumnIndex(latMin, 5);
        GridPane.setMargin(latMin, new Insets(10,10,0,10));
        //longDeg
        GridPane.setRowIndex(longDeg, 10);
        GridPane.setColumnIndex(longDeg, 5);
        GridPane.setMargin(longDeg, new Insets(10,10,0,10));
        //longMin
        GridPane.setRowIndex(longMin, 11);
        GridPane.setColumnIndex(longMin, 5);
        GridPane.setMargin(longMin, new Insets(10,10,0,10));

        //tripStops
        GridPane.setRowIndex(tripStops, 1);
        GridPane.setColumnIndex(tripStops, 8); //change value
        GridPane.setMargin(tripStops, new Insets(10,10,10,10));

        //total milage
        GridPane.setRowIndex(miles, 3);
        GridPane.setColumnIndex(miles, 8); //change value
        GridPane.setMargin(miles, new Insets(10,10,0,10));


        //CALCULATE AND PRINT TOTAL MILEAGE
        //totalMiles.calculateDistance(0,0,1,1); //CHANGE THIS TODO



        //*****LIST VIEWS*****
        GridPane.setRowIndex(places, 6);
        GridPane.setColumnIndex(places, 0);
        GridPane.setColumnSpan(places,2);
        GridPane.setRowSpan(places,20);
        GridPane.setMargin(places, new Insets(0,10,10,10));


        GridPane.setRowIndex(stops, 2);
        GridPane.setColumnIndex(stops, 8);
        GridPane.setColumnSpan(stops,2); //CHANGE SPANS
        GridPane.setRowSpan(stops,1);
        GridPane.setMargin(stops, new Insets(0,10,10,10));




        primaryStage.setScene(new Scene(root, 1000, 850));
        primaryStage.show();

    }

}
