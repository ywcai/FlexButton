package ywcai.ls.control.flex;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class FlexButtonLayout extends FlexboxLayout {
    private Context context;
    private OnFlexButtonClickListener onFlexButtonClickListener;
    private List<String> tags;
    private boolean isSelectOnlyOne, isShowSelectAll;
    private int[] selectIndex;
    private int currentIndex;
    private int btnMarginTop, btnMarginLeft, btnMarginBottom, btnMarginRight, btnBorderSize,
            btnPaddingTop, btnPaddingLeft, btnPaddingBottom, btnPaddingRight;
    private int btnWidth, btnHeight, btnTextSize, btnRadius;
    private int
            btnBorderColor,
            btnBgColor,
            btnTextColor,
            btnUnSelectTextColor,
            btnfocusColor,
            btnDisplayBgColor,
            btnDisplayTextColor;

    public FlexButtonLayout(Context context) {
        super(context);
        this.context = context;
        initStaticAttr(context, null, 0);
        setSampleDate(context, null, 0);
    }

    public FlexButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initStaticAttr(context, attrs, 0);
        setSampleDate(context, attrs, 0);
    }

    public FlexButtonLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initStaticAttr(context, attrs, defStyleAttr);
        setSampleDate(context, attrs, defStyleAttr);
    }

    public void initStaticAttr(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.FlexButtonLayout, defStyle, 0);
        isSelectOnlyOne = a.getBoolean(R.styleable.FlexButtonLayout_isSelectOnlyOne, false);
        isShowSelectAll = a.getBoolean(R.styleable.FlexButtonLayout_isShowSelectAll, true);
        btnBorderColor = a.getColor(R.styleable.FlexButtonLayout_btnBorderColor, ContextCompat.getColor(context, R.color.LOrange));
        btnBgColor = a.getColor(R.styleable.FlexButtonLayout_btnBgColor, ContextCompat.getColor(context, R.color.LOrange));
        btnfocusColor = a.getColor(R.styleable.FlexButtonLayout_btnfocusColor, ContextCompat.getColor(context, R.color.LOrange));
        btnUnSelectTextColor = a.getColor(R.styleable.FlexButtonLayout_btnUnSelectTextColor, ContextCompat.getColor(context, R.color.LDarkLight));
        btnDisplayBgColor = a.getColor(R.styleable.FlexButtonLayout_btnDisplayBgColor, ContextCompat.getColor(context, R.color.LDarkLight));
        btnDisplayTextColor = a.getColor(R.styleable.FlexButtonLayout_btnDisplayTextColor, Color.WHITE);
        btnTextColor = a.getColor(R.styleable.FlexButtonLayout_btnDisplayTextColor, Color.WHITE);
        btnMarginTop = a.getInteger(R.styleable.FlexButtonLayout_btnMarginTop, 10);
        btnMarginLeft = a.getInteger(R.styleable.FlexButtonLayout_btnMarginLeft, 10);
        btnMarginBottom = a.getInteger(R.styleable.FlexButtonLayout_btnMarginBottom, 10);
        btnMarginRight = a.getInteger(R.styleable.FlexButtonLayout_btnMarginRight, 10);

        btnPaddingTop = a.getInteger(R.styleable.FlexButtonLayout_btnPaddingTop, 5);
        btnPaddingLeft = a.getInteger(R.styleable.FlexButtonLayout_btnPaddingLeft, 10);
        btnPaddingBottom = a.getInteger(R.styleable.FlexButtonLayout_btnPaddingBottom, 5);
        btnPaddingRight = a.getInteger(R.styleable.FlexButtonLayout_btnPaddingRight, 10);

        btnWidth = a.getInteger(R.styleable.FlexButtonLayout_btnWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnHeight = a.getInteger(R.styleable.FlexButtonLayout_btnHeight, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnRadius = a.getInteger(R.styleable.FlexButtonLayout_btnRadius, 4);
        btnTextSize = a.getInteger(R.styleable.FlexButtonLayout_btnTextSize, 10);
        btnBorderSize = a.getInteger(R.styleable.FlexButtonLayout_btnBorderSize, 1);
        a.recycle();
    }

    public void setSampleDate(Context context, AttributeSet attrs, int defStyle) {
        List<String> sample = new ArrayList<>();
        sample.add("标签一");
        sample.add("标签二");
        sample.add("标签三");
        setDataAdapter(sample);
    }

    public void setOnFlexButtonClickListener(OnFlexButtonClickListener onFlexButtonClickListener) {
        this.onFlexButtonClickListener = onFlexButtonClickListener;
    }

    public void setDataAdapter(List<String> listText) {
        tags = listText;
        selectIndex = new int[tags.size()];
        selectIndex[0] = 1;
        currentIndex = -1;
        invalidate();
    }

    public void invalidate() {
        //清除示列的标签
        this.removeAllViews();
        //绘制自定义的数据标签;
        createTag();
        //重新绘制全选、取消键
        if (!isSelectOnlyOne && isShowSelectAll) {
            createTagSelectAll();
        }
    }

    private void createTag() {
        for (int i = 0; i < tags.size(); i++) {
            FancyButton fancyButton = new FancyButton(context);
            invalidateBtn(fancyButton);
            fancyButton.setText(tags.get(i) + "");
            fancyButton.setTag(i);
            if (isSelectOnlyOne) {
                setBtnSelectStatus(fancyButton, i == currentIndex);
                setOnClickForSingleMode(fancyButton);
            } else {
                setBtnSelectStatus(fancyButton, selectIndex[i] == 1);
                setOnClickForMultipleMode(fancyButton);
            }
            this.addView(fancyButton);
        }
    }

    private void setOnClickForSingleMode(FancyButton fancyButton) {
        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                boolean isSelectItem = (pos == currentIndex);
                //如果点击的按钮现在本身已经被选择，当选标签的情况下则不作任何处理
                if (!isSelectItem) {
                    //先改变上一次选择的标签;

                    if(currentIndex>=0) {
                        FancyButton prevSelect = ((FancyButton) ((FlexboxLayout) v.getParent()).getChildAt(currentIndex));
                        //将选择按钮位置同步为当前选择的位置
                        setBtnSelectStatus(prevSelect, false);
                    }
                    currentIndex = pos;
                    //改变当前选择的按钮的状态
                    setBtnSelectStatus((FancyButton) v, true);
                    if (onFlexButtonClickListener != null) {
                        onFlexButtonClickListener.clickItem(currentIndex, true);
                    }
                }
            }
        });
    }

    private void setOnClickForMultipleMode(FancyButton fancyButton) {
        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                selectIndex[pos] = selectIndex[pos] == 1 ? 0 : 1;
                //更新该标签的状态
                setBtnSelectStatus((FancyButton) v, selectIndex[pos] == 1);
                //看是否添加了尾部的全选按钮，如果添加了全选\取消按钮，则检测并更新到正确状态;
                if (isShowSelectAll) {
                    boolean isSelectAll = false;
                    for (int i = 0; i < selectIndex.length; i++) {
                        if (selectIndex[i] == 0) {
                            isSelectAll = false;
                            break;
                        }
                        isSelectAll = true;
                    }
                    //更新全选按钮的状态
                    FancyButton allSelectBtn = (FancyButton) ((FlexButtonLayout) v.getParent()).getChildAt(selectIndex.length);
                    setBtnAllSelectStatus(allSelectBtn, isSelectAll);
                }
                //参数二表示点击后，该选项是否被选择
                if (onFlexButtonClickListener != null) {
                    onFlexButtonClickListener.clickItem((int) v.getTag(), selectIndex[pos] == 1);
                }
            }
        });

    }


    private void createTagSelectAll() {
        FancyButton fancyButton = new FancyButton(context);
        invalidateBtn(fancyButton);
        boolean isSelectAll = false;
        for (int i = 0; i < selectIndex.length; i++) {
            if (selectIndex[i] == 0) {
                isSelectAll = false;
                break;
            }
            isSelectAll = true;
        }
        setBtnAllSelectStatus(fancyButton, isSelectAll);
        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //同时处理选择的索引数据和UI
                boolean currentSelectStatus = !((boolean) v.getTag());
                if (currentSelectStatus) {
                    selectIndex = new int[tags.size()];
                    for (int i = 0; i < tags.size(); i++) {
                        selectIndex[i] = 1;
                    }
                } else {
                    selectIndex = new int[tags.size()];
                }
                for (int i = 0; i < tags.size(); i++) {
                    FancyButton fancyButton = (FancyButton) ((FlexButtonLayout) v.getParent()).getChildAt(i);
                    setBtnSelectStatus(fancyButton, currentSelectStatus);
                }
                setBtnAllSelectStatus((FancyButton) v, currentSelectStatus);
                if (onFlexButtonClickListener != null) {
                    onFlexButtonClickListener.clickAllBtn(selectIndex, currentSelectStatus);
                }
            }
        });
        this.addView(fancyButton);
    }


    private void invalidateBtn(FancyButton fancyButton) {
        fancyButton.setBackgroundColor(btnBgColor);
        fancyButton.setBorderColor(btnBorderColor);
        fancyButton.setFocusBackgroundColor(btnfocusColor);
        fancyButton.setDisableBackgroundColor(btnDisplayBgColor);
        fancyButton.setDisableBorderColor(btnDisplayBgColor);
        fancyButton.setDisableTextColor(btnDisplayTextColor);
        fancyButton.setRadius(btnRadius);
        fancyButton.setTextSize(btnTextSize);
        fancyButton.setBorderWidth(btnBorderSize);
        fancyButton.setPadding(btnPaddingLeft, btnPaddingTop, btnPaddingRight, btnPaddingBottom);
        LayoutParams lay = new LayoutParams(btnWidth, btnHeight);
        lay.setMargins(btnMarginLeft, btnMarginTop, btnMarginRight, btnMarginBottom);
        fancyButton.setLayoutParams(lay);
    }

    private void setBtnSelectStatus(FancyButton button, boolean isSelect) {
        button.setGhost(!isSelect);
        if (isSelect) {
            button.setTextColor(btnTextColor);
        } else {
            button.setTextColor(btnUnSelectTextColor);
        }
    }


    private void setBtnAllSelectStatus(FancyButton button, boolean isSelect) {
        button.setTag(isSelect);
        button.setGhost(!isSelect);
        if (!isSelect) {
            button.setTextColor(btnUnSelectTextColor);
            button.setText("全选");
        } else {
            button.setTextColor(btnTextColor);
            button.setText("取消");
        }
    }

    //可动态调整某个位置标签的背景和边框颜色，字体颜色不能改变必须与整体设置保持一致
    //主要考虑单独设置某个按钮的颜色，无需在重绘所有UI
    public void setBtnSecondaryBgColor(int pos, int secondaryColor) {
        this.getChildAt(pos).setBackgroundColor(secondaryColor);
        ((FancyButton) this.getChildAt(pos)).setBorderColor(secondaryColor);
    }


    public int[] getSelectIndex() {
        return selectIndex;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    //用于初始化标签是否处于选择状态，多选模式下适用
    public void setSelectIndex(int[] selectIndex) {
        if (isSelectOnlyOne) {
            return;
        }
        if (selectIndex.length == tags.size()) {
            this.selectIndex = selectIndex;
            invalidate();
        }
    }

    //单选选模式下适用
    public void setCurrentIndex(int pos) {
        if (!isSelectOnlyOne) {
            return;
        }
        if (pos < 0) {
            return;
        }
        if (pos < tags.size() && pos >= 0) {
            if (currentIndex >= 0) {
                FancyButton prevSelect = (FancyButton) this.getChildAt(currentIndex);
                setBtnSelectStatus(prevSelect, false);
            }
            FancyButton currentSelect = (FancyButton) this.getChildAt(pos);
            setBtnSelectStatus(currentSelect, true);
            this.currentIndex = pos;
        }
    }


    //常规属性建议到属性里面设置//
    //    public void setBtnUnSelectColor(int btnUnSelectTextColor) {
//        this.btnUnSelectTextColor = btnUnSelectTextColor;
//    }
    //  public void setBtnTextSize(int btnTextSize) {
//        this.btnTextSize = btnTextSize;
//    }
//
//    public void setBtnRadius(int btnRadius) {
//        this.btnRadius = btnRadius;
//    }
//    public void setBtnMarginTop(int btnMarginTop) {
//        this.btnMarginTop = btnMarginTop;
//    }
//
//    public void setBtnMarginLeft(int btnMarginLeft) {
//        this.btnMarginLeft = btnMarginLeft;
//    }
//
//    public void setBtnMarginBottom(int btnMarginBottom) {
//        this.btnMarginBottom = btnMarginBottom;
//    }
//
//    public void setBtnMarginRight(int btnMarginRight) {
//        this.btnMarginRight = btnMarginRight;
//    }
//
//    public void setBtnBorderSize(int btnBorderSize) {
//        this.btnBorderSize = btnBorderSize;
//    }
//
//    public void setBtnWidth(int btnWidth) {
//        this.btnWidth = btnWidth;
//    }
//
//    public void setBtnHeight(int btnHeight) {
//        this.btnHeight = btnHeight;
//    }
//
//    public void setBtnUnSelectTextColor(int btnUnSelectTextColor) {
//        this.btnUnSelectTextColor = btnUnSelectTextColor;
//    }
//
//    public void setBtnBorderColor(int btnBorderColor) {
//        this.btnBorderColor = btnBorderColor;
//    }
//
//    public void setBtnBgColor(int btnBgColor) {
//        this.btnBgColor = btnBgColor;
//    }
//
//    public void setBtnTextColor(int btnTextColor) {
//        this.btnTextColor = btnTextColor;
//    }
//
//    public void setBtnfocusColor(int btnfocusColor) {
//        this.btnfocusColor = btnfocusColor;
//    }
//
//    public void setBtnDisplayBgColor(int btnDisplayBgColor) {
//        this.btnDisplayBgColor = btnDisplayBgColor;
//    }
//
//    public void setBtnDisplayTextColor(int btnDisplayTextColor) {
//        this.btnDisplayTextColor = btnDisplayTextColor;
//    }


}
