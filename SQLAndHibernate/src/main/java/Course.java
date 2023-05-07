import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    private int id;

    private String name;
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum( \"design\"," +
            "\"programming\"," +
            "\"marketing\"," +
            "\"management\"," +
            "\"business\")")
    private CourseType type;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "students_count")
    private int studentsCount;

    private double price;

    @Column(name = "price_per_hour")
    private float pricePerHour;
}
