package in.techaddicts.bluenix.Interface;

import in.techaddicts.bluenix.Model.Studio;

import java.util.List;

public interface IStudioBandLoadListener {
    void onStudioBandLoadSuccess(List<Studio> studioList);
    void onStudioBandLoadFailed(String message);
}
