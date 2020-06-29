package com.hallelujah.newwave.worship.count.model;

import io.realm.RealmObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Sector extends RealmObject {
    private static final int MAX_SECTOR = 6;

    // 0번부터 시작, 뷰는 1번부터 표시
    private int currentSector = 0;
    private int maxSector = MAX_SECTOR;
}
