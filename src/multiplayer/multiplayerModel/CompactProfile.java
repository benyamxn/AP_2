package multiplayer.multiplayerModel;

import java.io.Serializable;

public class CompactProfile implements Serializable {

    private String name;
    private String id;
    public CompactProfile(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public boolean equals(CompactProfile compactProfile) {
        return  this.id.equals(compactProfile.id);
    }
}
