package javaweb.AnGiangTourism.dto.place;

public interface PlaceProjection {
    String getId();
    String getName();
    String getAddress();
    String getHotLine();
    String getImage();
    Integer getMinPrice();
    Integer getMaxPrice();
    String getDescription();
    double getLon();
    double getLat();
}
