package in.techaddicts.bluenix.Interface;

import in.techaddicts.bluenix.Model.Banner;

import java.util.List;

public interface IBannerLoadListener {
    void onBannerLoadSuccess (List<Banner> banners);
    void onBannerLoadFailed (String message);
}
