package com.cy;

import java.util.ArrayList;
import java.util.List;

public class FastCreate {
    private List<WidgetInfo> listWeidget;
    private List<WidgetInfo> widgetList;
    private String contentInfo;
    private int level;
    private static final int LEVEL_DECLARE = 10;
    private static final int LEVEL_FINDVIEW = 20;
    private static final int LEVEL_CASE_ID = 30;
    private static final int LEVEL_ONCLICKLISTENER = 40;

    public FastCreate(List<WidgetInfo> list) {
        this.listWeidget = list;
        widgetList = new ArrayList<WidgetInfo>();
    }

    public void getContent() {
        for (int i = 0, len = listWeidget.size(); i < len; i++) {
            handWidgets(listWeidget.get(i).getType(), i);
        }

        // 排序，将level按从低向高排序
        for (int i = 0, len = widgetList.size(); i < len; i++) {
            for (int j = i + 1, lenj = widgetList.size(); j < lenj; j++) {
                if (widgetList.get(i).getLevel() > widgetList.get(j).getLevel()) {
                    WidgetInfo temp = new WidgetInfo();
                    temp = widgetList.get(j);
                    widgetList.add(i, temp);

                    temp = widgetList.get(i + 1);
                    widgetList.remove(i + 1);

                    widgetList.add(j, temp);
                    widgetList.remove(j + 1);
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        boolean isRun = false;

        int preLevel = 10;
        int nextLevel = 10;
        for (int i = 0, len = widgetList.size(); i < len; i++) {
            nextLevel = widgetList.get(i).getLevel();
            if (preLevel != nextLevel) {
                sb.append("\n\n");
                preLevel = nextLevel;
            }

            if (widgetList.get(i).getLevel() != LEVEL_ONCLICKLISTENER) {
                sb.append(widgetList.get(i).getContent());
                continue;
            }
            if (widgetList.get(i).getLevel() == LEVEL_ONCLICKLISTENER) {
                if (!isRun) {
                    sb.append(" OnClickListener mOnClickListener = new OnClickListener() {"
                            + "\n   public void onClick(View v) {"
                            + "\n        switch (v.getId()) {");
                    isRun = true;
                }

                sb.append(widgetList.get(i).getContent());
            }
            if (i == len - 1) {
                sb.append("\n    }"
                        + "\n  }"
                        + "\n};");
            }
        }

        System.out.println(sb.toString());
    }

    private void handWidgets(String type, int i) {
        String content = listWeidget.get(i).getIdName();
        level = LEVEL_DECLARE;
        contentInfo = get_output_content_by_level(content, type, level);
        createWidgetInfo(contentInfo, type, level, widgetList);

        level = LEVEL_FINDVIEW;
        contentInfo = get_output_content_by_level(content, type, level);
        createWidgetInfo(contentInfo, type, level, widgetList);

        if (type.equals("Button")) {
            contentInfo = " " + content + ".setOnClickListener(mOnClickListener);\n";
            level = LEVEL_CASE_ID;
            createWidgetInfo(contentInfo, type, level, widgetList);

            contentInfo = "\n        case R.id." + content + ":\n        break;";
            level = LEVEL_ONCLICKLISTENER;
            createWidgetInfo(contentInfo, type, level, widgetList);
        }

    }

    private String get_output_content_by_level(String content, String type, int level) {
        String str = null;
        switch (level) {
            case LEVEL_DECLARE:
                str = " private " + type + " " + content + ";\n";
                break;
            case LEVEL_FINDVIEW:
                str = " " + content + " = (" + type + ")findViewById(R.id." + content + ");\n";
                break;
            default:
                break;
        }
        return str;
    }

    private void createWidgetInfo(String content, String type, int level, List<WidgetInfo> widgetList) {
        WidgetInfo WidgetInfo = new WidgetInfo();
        WidgetInfo.setContent(content);
        WidgetInfo.setType(type);
        WidgetInfo.setLevel(level);
        widgetList.add(WidgetInfo);
    }
}
