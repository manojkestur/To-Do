package org.springapp.factory;

import org.springapp.enums.SortBy;
import org.springapp.strategy.DueDateSort;
import org.springapp.strategy.PrioritySort;
import org.springapp.strategy.SortByStrategy;

import static org.springapp.enums.SortBy.DUEDATE;
import static org.springapp.enums.SortBy.PRIORITY;

public class SortByFactory {

    public static SortByStrategy getSortByStrategyFactory(SortBy sortBy){
        SortByStrategy sortByStrategy=null;
        switch (sortBy){
            case DUEDATE:
                sortByStrategy = new DueDateSort();
                break;
            case PRIORITY:
                sortByStrategy = new PrioritySort();
                break;
        }
        return sortByStrategy;
    }
}
