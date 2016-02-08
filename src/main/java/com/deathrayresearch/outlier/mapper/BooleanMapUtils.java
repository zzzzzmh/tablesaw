package com.deathrayresearch.outlier.mapper;

import com.deathrayresearch.outlier.columns.BooleanColumn;
import com.deathrayresearch.outlier.columns.Column;

/**
 *   An interface for mapping operations unique to Boolean columns
 */
public interface BooleanMapUtils extends Column {

  /*
   * TODO(lwhite): Replace this implementation with a roaring bitmap version
   */
  default BooleanColumn and(BooleanColumn... columns) {
    BooleanColumn newColumn = BooleanColumn.create("");
    BooleanColumn thisColumn = (BooleanColumn) this;
    for (int i = 0; i < this.size(); i++) {
      boolean booleanValue = thisColumn.get(i);
      if (!booleanValue) {
        newColumn.set(i, false);
      } else {
        boolean result = true;
        for (BooleanColumn booleanColumn : columns) {
          result = booleanColumn.get(i);
          if (!result) {
            newColumn.set(i, false);
            break;
          }
        }
        newColumn.set(i, result);
      }
    }
    return newColumn;
  }

  default BooleanColumn or(BooleanColumn... columns) {
    BooleanColumn newColumn = BooleanColumn.create("");
    BooleanColumn thisColumn = (BooleanColumn) this;

    for (int i = 0; i < this.size(); i++) {
      Comparable value = thisColumn.get(i);
      boolean booleanValue = (Boolean) value;
      if (booleanValue) {
        newColumn.set(i, true);
      } else {
        boolean result = false;
        for (BooleanColumn booleanColumn : columns) {
          result = booleanColumn.get(i);
          if (result) {
            newColumn.set(i, true);
            break;
          }
        }
        newColumn.set(i, result);
      }
    }
    return newColumn;
  }
}
