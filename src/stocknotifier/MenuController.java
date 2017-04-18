package stocknotifier;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.*;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by anthony on 4/4/17.
 */
public class MenuController implements Initializable {
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public JFXButton requestTabButton;
    @FXML
    public JFXButton localStockTabButton;
    @FXML
    public Pane contentPane;
    @FXML
    public VBox sideBar;
    @FXML
    public JFXButton notifyButton;

    private JFXButton requestButton;
    private JFXButton deleteButton;
    private ObservableList<Product> localProducts;
    private JFXTreeTableView<Product> localStockTreeView;

    private JFXButton acceptButton;
    private ObservableList<Product> requestedProducts;
    private JFXTreeTableView<Product> requestedStockTreeView;
    private TrayNotification trayNotification = new TrayNotification();

    private boolean onRequestTab;

    //TODO Implement Firebase
//    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
//    private DatabaseReference ref = database.getReference("stock");


    public void requestTabButtonPressed(ActionEvent actionEvent) {
        requestTabButton.setButtonType(JFXButton.ButtonType.RAISED);
        localStockTabButton.setButtonType(JFXButton.ButtonType.FLAT);

        if (!onRequestTab) {
            contentPane.getChildren().remove(localStockTreeView);
            contentPane.getChildren().remove(requestButton);
            contentPane.getChildren().remove(deleteButton);
            contentPane.getChildren().add(requestedStockTreeView);
            contentPane.getChildren().add(acceptButton);
            onRequestTab = true;
        }


    }

    public void localStockTabButtonPressed(ActionEvent actionEvent) {
        requestTabButton.setButtonType(JFXButton.ButtonType.FLAT);
        localStockTabButton.setButtonType(JFXButton.ButtonType.RAISED);

        if (onRequestTab) {
            contentPane.getChildren().add(localStockTreeView);
            contentPane.getChildren().add(requestButton);
            contentPane.getChildren().add(deleteButton);
            contentPane.getChildren().remove(requestedStockTreeView);
            contentPane.getChildren().remove(acceptButton);
            onRequestTab = false;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notifyButton.setVisible(false);
        sideBar.setSpacing(5);
        //TODO Get Data from Firebase
//        DatabaseReference newPostRef = ref.push();
//        newPostRef.setValue(new Product("HG-0012", "Baju Dinosaurus", "Baju", "Rp. 100.000", "Dago"));

        initLocalStockTreeView();
        initRequestedStockTreeView();
        initButtons();

        onRequestTab = true;
        contentPane.getChildren().add(requestedStockTreeView);
        contentPane.getChildren().add(acceptButton);
    }

    private void initButtons() {
        deleteButton = new JFXButton("Delete");
        deleteButton.setLayoutY(500.0);
        deleteButton.setLayoutX(100.0);
        deleteButton.setOnAction(this::deleteButtonPressed);
        deleteButton.setButtonType(JFXButton.ButtonType.RAISED);
        deleteButton.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Paint.valueOf("rgb(255,107,107)"), CornerRadii.EMPTY, Insets.EMPTY)));

        requestButton = new JFXButton("Request");
        requestButton.setLayoutY(500.0);
        requestButton.setOnAction(this::requestButtonPressed);
        requestButton.setButtonType(JFXButton.ButtonType.RAISED);
        requestButton.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Paint.valueOf("rgb(198,243,100)"), CornerRadii.EMPTY, Insets.EMPTY)));

        if (localProducts.isEmpty()) {
            deleteButton.setDisable(true);
            requestButton.setDisable(true);
        }
        acceptButton = new JFXButton("Accept Request");
        acceptButton.setLayoutY(500.0);
        acceptButton.setOnAction(this::acceptButtonPressed);
        acceptButton.setButtonType(JFXButton.ButtonType.RAISED);
        acceptButton.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Paint.valueOf("rgb(198,243,100)"), CornerRadii.EMPTY, Insets.EMPTY)));

        if (requestedProducts.isEmpty()) {
            acceptButton.setDisable(true);
        }
    }

    private void initLocalStockTreeView() {
        localStockTreeView = new JFXTreeTableView<>();
        localStockTreeView.setPrefHeight(490.0);

        JFXTreeTableColumn<Product, String> idCol = new JFXTreeTableColumn<>("ID Product");
        idCol.setPrefWidth(150);
        idCol.setCellValueFactory(param -> param.getValue().getValue().idProp);

        JFXTreeTableColumn<Product, String> nameCol = new JFXTreeTableColumn<>("Name");
        nameCol.setPrefWidth(150);
        nameCol.setCellValueFactory(param -> param.getValue().getValue().nameProp);

        JFXTreeTableColumn<Product, String> typeCol = new JFXTreeTableColumn<>("Type");
        typeCol.setPrefWidth(150);
        typeCol.setCellValueFactory(param -> param.getValue().getValue().typeProp);

        JFXTreeTableColumn<Product, String> priceCol = new JFXTreeTableColumn<>("Price");
        priceCol.setPrefWidth(150);
        priceCol.setCellValueFactory(param -> param.getValue().getValue().priceProp);

        localProducts = FXCollections.observableArrayList();
        localProducts.add(new Product("67857-706", "Matsoft", "TENEX", "Rp. 390.583", "Bello"));
        localProducts.add(new Product("66336-427", "Toughjoyfax", "Levothyroxine Sodium", "Rp. 693.254", "Yingkeng"));
        localProducts.add(new Product("43071-110", "Pannier", "MIRAE HAIR", "Rp. 664.728", "Sukabatu"));
        localProducts.add(new Product("48951-7009", "Gembucket", "Mamma (dextra) 6", "Rp. 511.584", "El Ángel"));
        localProducts.add(new Product("67603-001", "Daltfresh", "Cellex-C Sun Care", "Rp. 884.522", "Kyshtym"));
        localProducts.add(new Product("54868-4909", "Tempsoft", "PREVPAC", "Rp. 967.805", "Vichuga"));
        localProducts.add(new Product("68084-777", "Regrant", "Arthritis Pain Reliever", "Rp. 91.821", "Taldom"));
        localProducts.add(new Product("0310-0145", "Flexidy", "ZESTORETIC", "Rp. 250.850", "Porirua"));
        localProducts.add(new Product("54162-018", "Lotlux", "CLOTRIMAZOLE", "Rp. 477.958", "Pondokrejo Wetan"));
        localProducts.add(new Product("54868-6363", "Stronghold", "Montelukast Sodium", "Rp. 657.927", "Borowno"));
        localProducts.add(new Product("67378-001", "Greenlam", "OXYGEN", "Rp. 536.379", "Chita"));
        localProducts.add(new Product("49035-080", "Holdlamis", "equate", "Rp. 339.024", "Heshi"));
        localProducts.add(new Product("37205-020", "Stringtough", "leader pain reliever", "Rp. 541.452", "Benhao"));
        localProducts.add(new Product("60429-312", "Lotstring", "Etodolac", "Rp. 792.199", "Chilecito"));
        localProducts.add(new Product("0496-0748", "Tres-Zap", "PRAX", "Rp. 773.869", "Shuangqiaoping"));
        localProducts.add(new Product("59779-229", "Pannier", "poly bacitracin", "Rp. 477.158", "Verkhniy Avzyan"));
        localProducts.add(new Product("47335-007", "Veribet", "Entacapone", "Rp. 354.474", "Jiuzhou"));
        localProducts.add(new Product("35356-636", "Kanlam", "FENTANYL TRANSDERMAL SYSTEM", "Rp. 924.907", "Ma’an"));
        localProducts.add(new Product("15127-170", "Fintone", "Select Brand Mucus Relief", "Rp. 434.480", "Kondinskoye"));
        localProducts.add(new Product("64942-0824", "Otcom", "Degree", "Rp. 580.807", "Bilar"));
        localProducts.add(new Product("54868-4860", "Hatity", "ENPRESSE", "Rp. 410.105", "São Domingos"));
        localProducts.add(new Product("0093-7355", "Zoolab", "Finasteride", "Rp. 765.824", "Calamba"));
        localProducts.add(new Product("55154-4768", "Solarbreeze", "VENLAFAXINE HYDROCHLORIDE", "Rp. 208.809", "Les Clayes-sous-Bois"));
        localProducts.add(new Product("49825-124", "Home Ing", "Breakout Control", "Rp. 64.445", "Madīnat ‘Īsá"));
        localProducts.add(new Product("51672-2106", "Y-Solowarm", "Cetirizine Hydrochloride", "Rp. 908.953", "Klang"));
        localProducts.add(new Product("36800-238", "Fixflex", "Stool Softener", "Rp. 219.326", "Ichinomiya"));
        localProducts.add(new Product("50845-0181", "Konklux", "Laxative", "Rp. 765.987", "Pedreira"));
        localProducts.add(new Product("0178-0500", "Keylex", "Lithostat", "Rp. 329.593", "Ke’erlun"));
        localProducts.add(new Product("0019-1333", "Cardify", "Optiray", "Rp. 789.124", "Bang Kaeo"));
        localProducts.add(new Product("64092-108", "Tampflex", "HEALTHCARE Instant Hand Sanitizer Vanilla Scent", "Rp. 241.142", "Yandun"));
        localProducts.add(new Product("0363-0173", "Fixflex", "complete lice treatment kit", "Rp. 62.427", "Kōriyama"));
        localProducts.add(new Product("11822-1261", "Job", "Childrens Ibuprofen", "Rp. 636.741", "Tōbetsu"));
        localProducts.add(new Product("76187-860", "Stronghold", "Rejuvity moisturizing day cream BROAD SPECTRUM SPF 15 SUNSCREEN", "Rp. 404.342", "Busay"));
        localProducts.add(new Product("49348-975", "Job", "sunmark nite time", "Rp. 685.652", "Bailizhou"));
        localProducts.add(new Product("49035-170", "Tresom", "Equate nicotine", "Rp. 737.053", "Banpu"));
        localProducts.add(new Product("0054-4218", "Flowdesk", "DOLOPHINE", "Rp. 863.166", "Calheta de Nesquim"));
        localProducts.add(new Product("0172-6406", "Bitchip", "Cromolyn Sodium", "Rp. 721.183", "Otavalo"));
        localProducts.add(new Product("59779-951", "Stim", "Instant Hand sanitizer", "Rp. 418.795", "Kokaj"));
        localProducts.add(new Product("53329-211", "Gembucket", "Epi-Clenz Instant Hand Antiseptic", "Rp. 399.128", "Pangpang"));
        localProducts.add(new Product("10544-593", "Fixflex", "Phentermine Hydrochloride", "Rp. 385.450", "Baitouli"));
        localProducts.add(new Product("0378-2222", "Trippledex", "Nisoldipine", "Rp. 106.696", "Doljo"));
        localProducts.add(new Product("47335-035", "Viva", "Zoledronic Acid", "Rp. 203.771", "Turangi"));
        localProducts.add(new Product("64578-0114", "Bigtax", "Drainage-Tone", "Rp. 385.716", "Carolina"));
        localProducts.add(new Product("21695-847", "Zathin", "Dexamethasone Sodium Phosphate", "Rp. 447.850", "Ozalj"));
        localProducts.add(new Product("0338-1083", "Lotlux", "CLINIMIX", "Rp. 911.418", "Sukamulya"));
        localProducts.add(new Product("0173-0843", "Rank", "AVANDARYL", "Rp. 632.227", "Hodkovičky"));
        localProducts.add(new Product("76237-157", "Bitwolf", "Famotidine", "Rp. 310.656", "Zīrakī"));
        localProducts.add(new Product("53150-317", "Pannier", "DOXOrubicin Hydrochloride", "Rp. 63.197", "Banjar Mambalkajanan"));
        localProducts.add(new Product("41250-277", "Sonsing", "Aspirin", "Rp. 643.475", "New Orleans"));
        localProducts.add(new Product("66184-400", "Redhold", "ck one3-in-1face makeup", "Rp. 129.130", "El Calafate"));
        localProducts.add(new Product("49349-550", "Fintone", "Prednisone", "Rp. 392.224", "Marseille"));
        localProducts.add(new Product("11673-368", "Sonair", "up and up naproxen sodium", "Rp. 79.955", "Old Shinyanga"));
        localProducts.add(new Product("64117-888", "Redhold", "Neuralgia Numbness", "Rp. 771.517", "Nāḩiyat Baḩār"));
        localProducts.add(new Product("65342-1393", "Tampflex", "Tinted Moisturizer SPF-20 Golden Radiance", "Rp. 509.500", "Unawatuna"));
        localProducts.add(new Product("60429-737", "Fixflex", "PAROXETINE", "Rp. 600.151", "Sollefteå"));
        localProducts.add(new Product("31722-225", "Temp", "Gemfibrozil", "Rp. 282.644", "Lazaro Cardenas"));
        localProducts.add(new Product("55154-7805", "Greenlam", "Levoxyl", "Rp. 898.582", "Dassun"));
        localProducts.add(new Product("65371-025", "Bytecard", "BB ANTI-FATIGUE ABSOLUTE SPF 25", "Rp. 868.169", "Huangli"));
        localProducts.add(new Product("10424-107", "Hatity", "SyImmune", "Rp. 226.042", "Hongsihu"));
        localProducts.add(new Product("43419-024", "Wrapsafe", "Sulwhasoo EVENFAIR SMOOTHING FOUNDATION", "Rp. 703.273", "Presidente Epitácio"));
        localProducts.add(new Product("49643-399", "Tin", "Black Cottonwood Pollen", "Rp. 363.906", "Bi’r al ‘Abd"));
        localProducts.add(new Product("68084-512", "Opela", "Simvastatin", "Rp. 346.238", "Shiogama"));
        localProducts.add(new Product("30142-076", "Rank", "Antacid", "Rp. 576.448", "Kwatarkwashi"));
        localProducts.add(new Product("52125-180", "Cookley", "CARBIDOPA AND LEVODOPA", "Rp. 819.802", "Krivaja"));
        localProducts.add(new Product("63833-518", "Solarbreeze", "Corifact", "Rp. 913.056", "Canguaretama"));
        localProducts.add(new Product("11410-044", "Stim", "Proactiv Repairing Treatment", "Rp. 556.608", "Qiaochong"));
        localProducts.add(new Product("52125-720", "Konklab", "Silver Sulfadiazene", "Rp. 276.801", "Alagoinhas"));
        localProducts.add(new Product("54569-1001", "Asoka", "Acetaminophen and Codeine Phosphate", "Rp. 350.062", "Nankengzi"));
        localProducts.add(new Product("46084-121", "Biodex", "DAYTIME NIGHTTIME COLD/FLU RELIEF", "Rp. 942.902", "Pandansari"));
        localProducts.add(new Product("0363-0891", "Tampflex", "athletes foot", "Rp. 141.239", "Urukh"));
        localProducts.add(new Product("50563-137", "Holdlamis", "RUE21 Cherry Antibacterial Hand Sanitizer", "Rp. 699.712", "Wuhe Chengguanzhen"));
        localProducts.add(new Product("16729-005", "Quo Lux", "simvastatin", "Rp. 791.697", "Salamrejo"));
        localProducts.add(new Product("11822-0812", "Rank", "Extra Strength Pain Relief", "Rp. 650.294", "Lipno"));
        localProducts.add(new Product("68645-300", "Toughjoyfax", "Metformin Hydrochloride", "Rp. 60.859", "Hennenman"));
        localProducts.add(new Product("13668-109", "Wrapsafe", "Duloxetine hydrochloride", "Rp. 51.568", "Strängnäs"));
        localProducts.add(new Product("0172-5034", "Kanlam", "Lisinopril and Hydrochlorothiazide", "Rp. 285.195", "Manggissari"));
        localProducts.add(new Product("67692-332", "Sonair", "Lumene Time Freeze Firming Day SPF 15 Sunscreen Broad Spectrum", "Rp. 177.341", "Kampungtengah"));
        localProducts.add(new Product("52125-942", "Transcof", "Loxapine", "Rp. 707.277", "Radom"));
        localProducts.add(new Product("42002-516", "Duobam", "Listerine Healthy White Anticavity Mouthrinse Gentle Clean Mint", "Rp. 557.093", "Young America"));
        localProducts.add(new Product("0472-0094", "Fintone", "Hair Regrowth Treatment For Men", "Rp. 278.252", "Dinjo"));
        localProducts.add(new Product("36987-2970", "Quo Lux", "Slash Pine", "Rp. 647.510", "Köln"));
        localProducts.add(new Product("51079-198", "Wrapsafe", "Tolterodine Tartrate", "Rp. 526.604", "Bitobe"));
        localProducts.add(new Product("52584-912", "Konklab", "Romazicon", "Rp. 361.168", "Rancho Nuevo"));
        localProducts.add(new Product("39892-0603", "Sonsing", "MediChoice Premium Anticavity Fluoride", "Rp. 717.583", "Toritama"));
        localProducts.add(new Product("63940-100", "Bytecard", "HARMON Extra Strength Pain Relieving", "Rp. 545.648", "Liufu"));
        localProducts.add(new Product("76125-900", "Trippledex", "GAMMAKED", "Rp. 533.111", "La Courneuve"));
        localProducts.add(new Product("54868-4198", "Treeflex", "Avandia", "Rp. 596.829", "Itaporanga"));
        localProducts.add(new Product("47066-402", "Konklab", "Foam Safe", "Rp. 498.585", "Podbrdo"));
        localProducts.add(new Product("41167-7449", "Sub-Ex", "Kaopectate Extra Strength Peppermint Flavor Anti Diarrheal", "Rp. 364.059", "Alexandria"));
        localProducts.add(new Product("54162-018", "Zathin", "CLOTRIMAZOLE", "Rp. 334.971", "Krenggan"));
        localProducts.add(new Product("59401-004", "Subin", "Manefit Bling Bling Whitening Platinum Hydrogel Mask", "Rp. 441.389", "Lammi"));
        localProducts.add(new Product("49348-506", "Domainer", "Sunmark migraine relief", "Rp. 977.535", "Lesichevo"));
        localProducts.add(new Product("76419-211", "Duobam", "Sodium Fluoride F 18", "Rp. 909.482", "Diónysos"));
        localProducts.add(new Product("11822-0300", "Quo Lux", "Hydrogen Peroxide", "Rp. 718.280", "Kattaqo’rg’on"));
        localProducts.add(new Product("0173-0712", "Regrant", "AVODART", "Rp. 873.448", "Cambita Garabitos"));

        final TreeItem<Product> root = new RecursiveTreeItem<>(localProducts, RecursiveTreeObject::getChildren);

        localStockTreeView.getColumns().setAll(idCol, nameCol, typeCol, priceCol);
        localStockTreeView.setRoot(root);
        localStockTreeView.setShowRoot(false);
    }

    private void initRequestedStockTreeView() {
        requestedStockTreeView = new JFXTreeTableView<>();
        requestedStockTreeView.setPrefHeight(490.0);

        JFXTreeTableColumn<Product, String> idCol = new JFXTreeTableColumn<>("ID Product");
        idCol.setPrefWidth(120);
        idCol.setCellValueFactory(param -> param.getValue().getValue().idProp);

        JFXTreeTableColumn<Product, String> nameCol = new JFXTreeTableColumn<>("Name");
        nameCol.setPrefWidth(120);
        nameCol.setCellValueFactory(param -> param.getValue().getValue().nameProp);

        JFXTreeTableColumn<Product, String> typeCol = new JFXTreeTableColumn<>("Type");
        typeCol.setPrefWidth(120);
        typeCol.setCellValueFactory(param -> param.getValue().getValue().typeProp);

        JFXTreeTableColumn<Product, String> priceCol = new JFXTreeTableColumn<>("Price");
        priceCol.setPrefWidth(120);
        priceCol.setCellValueFactory(param -> param.getValue().getValue().priceProp);

        JFXTreeTableColumn<Product, String> outletCol = new JFXTreeTableColumn<>("Outlet");
        outletCol.setPrefWidth(120);
        outletCol.setCellValueFactory(param -> param.getValue().getValue().outletProp);

        requestedProducts = FXCollections.observableArrayList();
        requestedProducts.add(new Product("0406-8096", "Bytecard", "Oxymorphone Hydrochloride", "Rp. 497.314", "Pavlysh"));
        requestedProducts.add(new Product("59316-104", "Stringtough", "Biofreeze", "Rp. 281.789", "Guanlu"));
        requestedProducts.add(new Product("57520-0846", "Veribet", "Bio Wild Rose", "Rp. 399.958", "Nizhniy Novgorod"));
        requestedProducts.add(new Product("63941-120", "Bitwolf", "womans laxative", "Rp. 765.302", "Gotse Delchev"));
        requestedProducts.add(new Product("42291-142", "Overhold", "Atenolol", "Rp. 648.719", "Daixi"));
        requestedProducts.add(new Product("54868-3619", "Fintone", "Humulin R", "Rp. 697.839", "Stráž"));
        requestedProducts.add(new Product("63187-189", "Zamit", "Azithromycin", "Rp. 742.799", "Papeete"));
        requestedProducts.add(new Product("24385-439", "Otcom", "Caldyphen Clear", "Rp. 859.818", "Lunec"));
        requestedProducts.add(new Product("59762-0059", "Zaam-Dox", "Alprazolam", "Rp. 657.486", "Kiupo"));
        requestedProducts.add(new Product("15955-327", "Sonsing", "LEVETIRACETAM", "Rp. 247.451", "Magdug"));
        requestedProducts.add(new Product("76237-246", "Home Ing", "Spironolactone", "Rp. 550.449", "Xarag"));
        requestedProducts.add(new Product("37205-345", "Bitchip", "Leader ibuprofen", "Rp. 166.489", "Karangampel"));
        requestedProducts.add(new Product("68084-111", "Matsoft", "Glipizide", "Rp. 104.591", "Gogaran"));
        requestedProducts.add(new Product("16590-952", "Fixflex", "LOSARTAN POTASSIUM", "Rp. 153.445", "Karangpaningal"));
        requestedProducts.add(new Product("0268-6101", "Zamit", "APPLE", "Rp. 667.473", "Tatariv"));
        requestedProducts.add(new Product("55154-3380", "It", "Quetiapine fumarate", "Rp. 789.612", "Villa del Carmen"));
        requestedProducts.add(new Product("60575-414", "Otcom", "Tricode", "Rp. 666.694", "Verkhnyachka"));
        requestedProducts.add(new Product("37000-325", "Home Ing", "Head and Shoulders 2in1", "Rp. 426.672", "Quezalguaque"));
        requestedProducts.add(new Product("49781-051", "Home Ing", "Leader Acid Control", "Rp. 582.501", "Grebnevo"));
        requestedProducts.add(new Product("67938-2008", "Trippledex", "FLAWLESS FINISH PERFECTLY NUDE MAKEUP BROAD SPECTRUM SUNSCREEN SPF 15 SHADE CREAM NUDE", "Rp. 885.246", "Purranque"));
        requestedProducts.add(new Product("61715-070", "Solarbreeze", "KPP Ultra Thin Corn Removers", "Rp. 290.190", "Chelyabinsk"));
        requestedProducts.add(new Product("67467-633", "Stringtough", "Albumin (Human)", "Rp. 936.444", "Wailang"));
        requestedProducts.add(new Product("11528-095", "Fintone", "Paire OB Plus DHA", "Rp. 193.784", "Asarum"));
        requestedProducts.add(new Product("67692-081", "Andalax", "Lumene Vitamin C Pure Radiance Day", "Rp. 920.798", "Lizi"));
        requestedProducts.add(new Product("48951-1062", "Quo Lux", "Apis regina Cerebri", "Rp. 969.440", "Lebao"));
        requestedProducts.add(new Product("68001-158", "Y-Solowarm", "Venlafaxine", "Rp. 263.505", "Bielawy"));
        requestedProducts.add(new Product("60512-9083", "Home Ing", "GAS AND COLIC KIDS RELIEF", "Rp. 419.404", "Télimélé"));
        requestedProducts.add(new Product("10742-8932", "Quo Lux", "Softlips Lip Relief", "Rp. 63.154", "Piña"));
        requestedProducts.add(new Product("76369-1001", "Sub-Ex", "Clara", "Rp. 143.731", "Kokubu-matsuki"));
        requestedProducts.add(new Product("54868-4810", "Andalax", "Androgel", "Rp. 420.658", "Bondokodi"));
        requestedProducts.add(new Product("0009-0039", "Sub-Ex", "Solu-Medrol", "Rp. 186.940", "Tomay Kichwa"));
        requestedProducts.add(new Product("51523-021", "Zontrax", "NATURAL SUN AQ SUPER PERFECT SUN", "Rp. 595.272", "Kadipaten"));
        requestedProducts.add(new Product("43857-0135", "Namfix", "Vax", "Rp. 836.199", "Tongyuanpu"));
        requestedProducts.add(new Product("54868-2317", "Stronghold", "Doxepin Hydrochloride", "Rp. 913.818", "Bang Mun Nak"));
        requestedProducts.add(new Product("37000-904", "Voltsillam", "Dolce and Gabbana The Lift Foundation Bronze 144", "Rp. 645.103", "Pontevedra"));
        requestedProducts.add(new Product("49349-777", "Domainer", "Isoniazid", "Rp. 523.514", "Dmytrivka"));
        requestedProducts.add(new Product("0270-1315", "Home Ing", "ISOVUE", "Rp. 928.694", "Kościerzyna"));
        requestedProducts.add(new Product("54569-2173", "Kanlam", "Lorazepam", "Rp. 173.501", "Mugan"));
        requestedProducts.add(new Product("0268-6206", "Solarbreeze", "POTATO", "Rp. 724.451", "As Suwaydā"));
        requestedProducts.add(new Product("0603-0441", "Tres-Zap", "Bacitracin zinc", "Rp. 648.634", "Slatyne"));
        requestedProducts.add(new Product("35000-623", "Kanlam", "Lady Speed Stick", "Rp. 995.953", "Effium"));
        requestedProducts.add(new Product("52685-453", "Voyatouch", "SHISEIDO ADVANCED HYDRO-LIQUID COMPACT (REFILL)", "Rp. 456.598", "Lajaluhur"));
        requestedProducts.add(new Product("68828-126", "Regrant", "Soft Beige Always color stay-on Makeup Broad Spectrum SPF 15", "Rp. 457.457", "Além Paraíba"));
        requestedProducts.add(new Product("48951-7040", "Zathin", "Mercurius auratus Pulmo", "Rp. 161.296", "Livramento"));
        requestedProducts.add(new Product("66116-478", "Overhold", "Amoxicillin and Clavulanate Potassium", "Rp. 669.573", "Midoun"));
        requestedProducts.add(new Product("40046-0037", "Overhold", "MINERALIZE FOUNDATION", "Rp. 320.637", "Cesvaine"));
        requestedProducts.add(new Product("52125-542", "Tresom", "Acetaminophen and Codeine", "Rp. 838.213", "Drachten"));
        requestedProducts.add(new Product("51079-921", "Greenlam", "Clozapine", "Rp. 993.162", "Molde"));
        requestedProducts.add(new Product("49967-583", "Trippledex", "Kiehls Since 1851 Facial Fuel Energizing Moisture Treatment for Men Broad Spectrum SPF 15 Sunscreen", "Rp. 563.674", "’Unābah"));
        requestedProducts.add(new Product("68788-9695", "Holdlamis", "levofloxacin", "Rp. 436.253", "Shahr-e Bābak"));
        requestedProducts.add(new Product("55150-168", "Tampflex", "Bupivacaine Hydrochloride", "Rp. 958.977", "Särevere"));
        requestedProducts.add(new Product("36800-143", "Vagram", "topcare nite time cold and flu", "Rp. 152.077", "Magnitka"));
        requestedProducts.add(new Product("10139-071", "Lotstring", "Ampicillin and Sulbactam", "Rp. 147.452", "Babao"));
        requestedProducts.add(new Product("64117-888", "Trippledex", "Neuralgia Numbness", "Rp. 308.495", "Huaizhong"));
        requestedProducts.add(new Product("50436-9055", "Konklux", "Metformin Hydrochloride", "Rp. 852.063", "Saint Petersburg"));
        requestedProducts.add(new Product("64117-120", "Sonair", "Rheumatic Pains", "Rp. 447.258", "Lahat"));
        requestedProducts.add(new Product("48951-9075", "Cardguard", "Viscum Echinacea", "Rp. 400.456", "Kobrinskoye"));
        requestedProducts.add(new Product("49884-323", "Ventosanzap", "Olanzapine", "Rp. 829.465", "Ntungamo"));
        requestedProducts.add(new Product("10191-1244", "Span", "ARNICA MONTANA", "Rp. 193.367", "Bang Kho Laem"));
        requestedProducts.add(new Product("49349-887", "Mat Lam Tam", "Omeprazole", "Rp. 892.901", "Romilly-sur-Seine"));
        requestedProducts.add(new Product("0363-0987", "Tampflex", "childrens wal tap dm", "Rp. 531.722", "General Ramírez"));
        requestedProducts.add(new Product("0575-6200", "Pannier", "Proglycem", "Rp. 306.233", "Hoani"));
        requestedProducts.add(new Product("55648-414", "Bytecard", "FELODIPINE", "Rp. 635.702", "Seremban"));
        requestedProducts.add(new Product("60512-6534", "Flowdesk", "AVENA SATIVA", "Rp. 993.142", "Tinumpuk"));
        requestedProducts.add(new Product("41163-279", "Tempsoft", "Equaline Hemorrhoidal", "Rp. 411.438", "Mandala"));
        requestedProducts.add(new Product("52685-447", "Fintone", "SHISEIDO ADVANCED HYDRO-LIQUID COMPACT (REFILL)", "Rp. 867.875", "Međa"));
        requestedProducts.add(new Product("47242-0061", "Bytecard", "Sleep RELIEF", "Rp. 651.366", "Lashkar Gāh"));
        requestedProducts.add(new Product("54569-5193", "Ronstring", "Amoxicillin", "Rp. 696.625", "Chigan"));
        requestedProducts.add(new Product("68516-4603", "Job", "Alphanate", "Rp. 434.121", "Choisy-le-Roi"));
        requestedProducts.add(new Product("0228-3261", "Domainer", "Oxymorphone hydrochloride", "Rp. 113.373", "Pasirlaja"));
        requestedProducts.add(new Product("59535-2701", "Kanlam", "ULTRA HYDRA", "Rp. 783.575", "Lesnikovo"));
        requestedProducts.add(new Product("51079-917", "Y-find", "Verapamil Hydrochloride", "Rp. 809.467", "Afikpo"));
        requestedProducts.add(new Product("68428-015", "Lotstring", "Calcarea Phos Kit Refill", "Rp. 55.948", "San Francisco"));
        requestedProducts.add(new Product("41520-981", "Duobam", "care one cold head congestion", "Rp. 758.568", "Toungo"));
        requestedProducts.add(new Product("16590-068", "Duobam", "Dexamethasone Sodium Phosphate", "Rp. 592.696", "Bylym"));
        requestedProducts.add(new Product("0031-8732", "Veribet", "ROBITUSSIN PEAK COLD NIGHTTIME NASAL RELIEF", "Rp. 359.487", "Rabig"));
        requestedProducts.add(new Product("10631-285", "Domainer", "Desquam-X", "Rp. 500.406", "Kerva"));
        requestedProducts.add(new Product("52533-103", "Holdlamis", "Diltiazem HCl", "Rp. 284.946", "Kanungu"));
        requestedProducts.add(new Product("0363-9529", "Konklux", "Walgreens Anticavity and Antigingivitis Whitening", "Rp. 81.352", "Yuncheng"));
        requestedProducts.add(new Product("13668-013", "Fix San", "Metformin Hydrochloride", "Rp. 129.451", "Drummondville"));
        requestedProducts.add(new Product("51009-100", "Bamity", "Whole Care", "Rp. 504.165", "Kímolos"));
        requestedProducts.add(new Product("55154-6815", "Mat Lam Tam", "Sodium Bicarbonate Antacid", "Rp. 280.793", "Ivry-sur-Seine"));
        requestedProducts.add(new Product("0832-0038", "Biodex", "Oxybutynin Chloride", "Rp. 504.368", "Litian"));
        requestedProducts.add(new Product("36800-548", "Tempsoft", "topcare day time cold and flu severe", "Rp. 431.797", "Lubień Kujawski"));
        requestedProducts.add(new Product("67877-164", "Voyatouch", "Lamotrigine", "Rp. 52.894", "Volary"));
        requestedProducts.add(new Product("54868-1119", "Zamit", "PredniSONE", "Rp. 170.192", "Ajuy"));
        requestedProducts.add(new Product("68084-459", "Bytecard", "Nateglinide", "Rp. 263.713", "Alor Star"));
        requestedProducts.add(new Product("66949-607", "Cardguard", "Zep Provisions Pot and Pan Premium FF", "Rp. 56.730", "Rungkang"));
        requestedProducts.add(new Product("42043-193", "Zaam-Dox", "Levetiracetam", "Rp. 988.571", "Qitai"));
        requestedProducts.add(new Product("24488-003", "Asoka", "Boy Butter", "Rp. 992.673", "Kanye"));
        requestedProducts.add(new Product("68788-9512", "Y-Solowarm", "Sulfamethoxazole and Trimethoprim", "Rp. 229.764", "Duba-Yurt"));
        requestedProducts.add(new Product("50268-628", "Home Ing", "Oxybutynin Chloride", "Rp. 385.180", "Itaúna"));
        requestedProducts.add(new Product("49291-0003", "Cardguard", "Super Hero", "Rp. 352.755", "Langenburg"));
        requestedProducts.add(new Product("43115-123", "Regrant", "NIA 24 SPF 30", "Rp. 764.061", "Stockholm"));
        requestedProducts.add(new Product("59535-2801", "Gembucket", "REVITALIZING C", "Rp. 259.050", "Sidomulyo"));
        requestedProducts.add(new Product("48951-4073", "Treeflex", "Ferrum sidereum 6", "Rp. 593.140", "Las Vegas"));
        requestedProducts.add(new Product("0093-7188", "Cardify", "Fluoxetine", "Rp. 61.617", "Krajandadapmulyo"));
        requestedProducts.add(new Product("0064-1030", "Lotstring", "TRISEPTIN", "Rp. 349.430", "Yanping"));
        requestedProducts.add(new Product("52686-223", "Domainer", "SHISEIDO SUNCARE EXTRA SMOOTH", "Rp. 839.961", "Pôrto Barra do Ivinheima"));

        final TreeItem<Product> root = new RecursiveTreeItem<>(requestedProducts, RecursiveTreeObject::getChildren);

        requestedStockTreeView.getColumns().setAll(idCol, nameCol, typeCol, priceCol, outletCol);
        requestedStockTreeView.setRoot(root);
        requestedStockTreeView.setShowRoot(false);
    }


    private void deleteButtonPressed(ActionEvent actionEvent) {
        deleteLocalItem(false);
    }

    private void requestButtonPressed(ActionEvent event) {
        deleteLocalItem(true);
    }

    private void deleteLocalItem(boolean isRequest) {
        TreeItem<Product> selectedItem = localStockTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        Product selectedItemValue = selectedItem.getValue();
        if (isRequest) {
            Platform.runLater(() -> {
                JFXSnackbar responseSnackbar = new JFXSnackbar(anchorPane);
                responseSnackbar.show(selectedItemValue.productID + " requested", 2000);
            });
        }
        localProducts.remove(selectedItemValue);
        if (localProducts.isEmpty()) {
            deleteButton.setDisable(true);
            requestButton.setDisable(true);

        }
    }

    private void acceptButtonPressed(ActionEvent actionEvent) {
        deleteRequestedItem();
    }

    private void deleteRequestedItem() {
        TreeItem<Product> selectedItem = requestedStockTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        Product selectedItemValue = selectedItem.getValue();
        Platform.runLater(() -> {
            JFXSnackbar responseSnackbar = new JFXSnackbar(anchorPane);
            responseSnackbar.show(selectedItemValue.productID + " accepted", 2000);
        });
        requestedProducts.remove(selectedItemValue);
        if (requestedProducts.isEmpty()) {
            acceptButton.setDisable(true);
        }
    }

    public void notifyEmptyStock() {
        if (trayNotification.isTrayShowing()) return;
        Product testProduct = new Product("HG-0011", "Baju T-rex", "Baju", "Rp. 103.000");
        String title = "Empty Stock!";
        String message = testProduct.productName + " has gone empty!";
        NotificationType notification = NotificationType.WARNING;

        trayNotification.setTitle(title);
        trayNotification.setMessage(message);
        trayNotification.setNotificationType(notification);
        trayNotification.setAnimationType(AnimationType.POPUP);
        trayNotification.showAndDismiss(Duration.seconds(2));
        localProducts.add(testProduct);

    }



    class Product extends RecursiveTreeObject<Product> {

        StringProperty idProp;
        public String productID;
        StringProperty nameProp;
        public String productName;
        StringProperty typeProp;
        public String productType;
        StringProperty priceProp;
        public String productPrice;
        StringProperty outletProp;
        public String productOutlet;


        Product(String idProp, String nameProp, String typeProp, String priceProp) {
            this.idProp = new SimpleStringProperty(idProp);
            productID = idProp;
            this.nameProp = new SimpleStringProperty(nameProp);
            productName = nameProp;
            this.typeProp = new SimpleStringProperty(typeProp);
            productType = typeProp;
            this.priceProp = new SimpleStringProperty(priceProp);
            productPrice = priceProp;
        }

        Product(String idProp, String nameProp, String typeProp, String priceProp, String outletProp) {
            this(idProp, nameProp, typeProp, priceProp);
            this.outletProp = new SimpleStringProperty(outletProp);
            productOutlet = outletProp;
        }


    }
}
