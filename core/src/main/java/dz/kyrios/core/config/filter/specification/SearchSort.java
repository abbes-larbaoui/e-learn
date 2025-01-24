package dz.kyrios.core.config.filter.specification;

import lombok.Data;

@Data
public class SearchSort {
    private String selector;
    private boolean desc;

    public SearchSort() {
    }
}
