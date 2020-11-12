package in.techaddicts.bluenix.Interface;

import in.techaddicts.bluenix.Database.CartItem;

import java.util.List;

public interface ICartItemLoadListener {
    void onGetAllItemFromCartSuccess(List<CartItem> cartItemList);
}
