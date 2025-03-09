/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import entity.*;
import java.util.Comparator;

/**
 *
 * @author LIM CHEE LEONG
 * @param <T>
 */
public class CustomComparator<T> implements Comparator<T> {

    private EnumSortingCriterion criterion;

    public CustomComparator(EnumSortingCriterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public int compare(T o1, T o2) {
        if (criterion == EnumSortingCriterion.IDSTRING) {
            return compareByIdString(o1, o2);
        } else if (criterion == EnumSortingCriterion.NAME) {
            return compareByName(o1, o2);
        } else if (criterion == EnumSortingCriterion.IDNUMBER) {
            return compareByIdNum(o1, o2);
        }

        return 0;
    }

    private int compareByName(T o1, T o2) {
        String name1 = getName(o1);
        String name2 = getName(o2);
        return name1.compareTo(name2);
    }

    private int compareByIdString(T o1, T o2) {
        String id1 = getIdString(o1);
        String id2 = getIdString(o2);
        return id1.compareTo(id2);
    }

    private int compareByIdNum(T o1, T o2) {
        int id1 = getIdNum(o1);
        int id2 = getIdNum(o2);
        return Integer.compare(id1, id2);
    }

    private int getIdNum(T obj) {
        if (obj instanceof TutorialGroup) {

            String id = ((TutorialGroup) obj).getId();

            String numericPart = id.substring(1);

            int intValue = Integer.parseInt(numericPart);

            return intValue;
        } else if (obj instanceof Student) {
            String id = ((Student) obj).getId();

            String numericPart = id.substring(1);

            int intValue = Integer.parseInt(numericPart);

            return intValue;
        }

        return 0; // Handle other types accordingly
    }

    private String getIdString(T obj) {
        if (obj instanceof Programme) {
            return ((Programme) obj).getProgrammeId();
        } else if (obj instanceof Student) {
            String id = ((Student) obj).getId();

            return id;
        }
        return null; // Handle other types accordingly
    }

    private String getName(T obj) {
        if (obj instanceof Student) {
            return ((Student) obj).getName();
        }
        return null; // Handle other types accordingly
    }

}
