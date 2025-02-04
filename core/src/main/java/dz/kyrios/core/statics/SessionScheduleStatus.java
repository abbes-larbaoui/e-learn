package dz.kyrios.core.statics;

public enum SessionScheduleStatus {
    SCHEDULED,   // Session is planned
    COMPLETED,   // Session finished successfully
    CANCELED,    // Session was canceled by the teacher
    MISSED       // Student didn't attend
}
