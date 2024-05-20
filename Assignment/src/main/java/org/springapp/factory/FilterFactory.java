package org.springapp.factory;

import org.springapp.dto.Filters;
import org.springapp.strategy.DueDateFilter;
import org.springapp.strategy.FilterStrategy;
import org.springapp.strategy.PriorityFilter;

public class FilterFactory {

    public static FilterStrategy getFilterStrategyFactory(Filters filters){
        FilterStrategy filterStrategy=null;
        if(filters.getDueDate()!=null) {
            filterStrategy = new DueDateFilter();
        } else if(filters.getPriority()!=null){
            filterStrategy = new PriorityFilter();
        }
        return filterStrategy;
    }
}
