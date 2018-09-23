package testtask.partslist.entities;

import javax.persistence.*;

@Entity
@Table(name = "part")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "part_name")
    private String name;

    @Column(name = "part_need")
    private boolean need;

    @Column(name = "part_count")
    private int count;

    public Part(String name, boolean need, int count) {
        this.name = name;
        this.need = need;
        this.count = count;
    }

    public Part() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNeed() {
        return need;
    }

    public void setNeed(boolean need) {
        this.need = need;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", need=" + need +
                ", count=" + count +
                '}';
    }
}
