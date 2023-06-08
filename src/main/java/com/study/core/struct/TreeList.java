package com.study.core.struct;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * List 转换为树
 */
@Getter
@Setter
public class TreeList {
    private Long id;
    private String name;
    private Long parentId;
    private List<TreeList> children;

    public static List<TreeList> buildTreeTwoLayersFor(List<TreeList> zoneList) {
        List<TreeList> result = new ArrayList<>();
        for (TreeList zone : zoneList) {
            if (zone.getParentId().equals(0L)) {
                result.add(zone);
            }
            for (TreeList child : zoneList) {
                if (child.getParentId().equals(zone.getId())) {
                    List<TreeList> children = zone.getChildren();
                    if (children == null) {
                        children = new ArrayList<>();
                        zone.setChildren(children);
                    }
                    children.add(child);
                }
            }
        }
        return result;
    }
    public static List<TreeList> buildTreeMap(List<TreeList> zoneList) {
        Map<Long, List<TreeList>> zoneByParentIdMap = new HashMap<>();
        zoneList.forEach(zone -> {
            List<TreeList> children = zoneByParentIdMap.getOrDefault(zone.getParentId(), new ArrayList<>());
            children.add(zone);
            zoneByParentIdMap.put(zone.getParentId(), children);
        });
        zoneList.forEach(zone -> zone.setChildren(zoneByParentIdMap.get(zone.getId())));
        return zoneList.stream()
                .filter(v -> v.getParentId().equals(0L))
                .collect(Collectors.toList());
    }

    public static List<TreeList> buildTreeStream(List<TreeList> zoneList) {
        Map<Long, List<TreeList>> zoneByParentIdMap = zoneList.stream().collect(Collectors.groupingBy(TreeList::getParentId));
        zoneList.forEach(zone -> zone.setChildren(zoneByParentIdMap.get(zone.getId())));
        return zoneList.stream().filter(v -> v.getParentId().equals(0L)).collect(Collectors.toList());
    }


}
