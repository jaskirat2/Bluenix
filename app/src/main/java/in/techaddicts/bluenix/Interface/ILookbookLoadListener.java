package in.techaddicts.bluenix.Interface;

import in.techaddicts.bluenix.Model.Banner;

import java.util.List;

public interface ILookbookLoadListener {
    void onLookbookLoadSuccess (List<Banner> banners);
    void onLookbookLoadFailed (String message);
}
