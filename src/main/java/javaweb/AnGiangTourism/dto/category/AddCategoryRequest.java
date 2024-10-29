package javaweb.AnGiangTourism.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddCategoryRequest {

    @NotNull(message = "Category name is required")
    String name;
}
