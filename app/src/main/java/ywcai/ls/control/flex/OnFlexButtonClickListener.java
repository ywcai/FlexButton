package ywcai.ls.control.flex;

public interface OnFlexButtonClickListener {
    void clickItem(int pos, boolean itemCurrentSelectStatus);
    void clickAllBtn(int[] selectIndex, boolean isSelectAll);
}
