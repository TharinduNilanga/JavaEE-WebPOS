package bo;

import bo.custom.impl.*;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case ITEM:
                return new ItemBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case SIGNUP:
                return  new SignUpBOImpl();
            case ORDERDETAILS:
                return new OrderDetailsBOImpl();

            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, ORDER,SIGNUP,ORDERDETAILS
    }
}
