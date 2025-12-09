package com.smartpick.mysp.data

// Admin credentials data class
data class AdminUser(
    val adminId: String,
    val adminName: String,
    val adminPassword: String,
    val email: String,
    val role: String = "ADMIN"
)

// Predefined admin users (in production, use secure backend authentication)
object AdminCredentialsManager {
    private val validAdmins = listOf(
        AdminUser(
            adminId = "ADMIN001",
            adminName = "Admin",
            adminPassword = "admin123",
            email = "admin@smartpick.com",
            role = "SUPER_ADMIN"
        ),
        AdminUser(
            adminId = "ADMIN002",
            adminName = "Manager",
            adminPassword = "manager456",
            email = "manager@smartpick.com",
            role = "ADMIN"
        )
    )

    /**
     * Validate admin credentials
     * @param adminName The admin username/name
     * @param adminPassword The admin password
     * @return AdminUser if credentials are valid, null otherwise
     */
    fun validateCredentials(adminName: String, adminPassword: String): AdminUser? {
        return validAdmins.find { admin ->
            admin.adminName.equals(adminName, ignoreCase = true) &&
            admin.adminPassword == adminPassword
        }
    }

    /**
     * Check if a user is a valid admin
     * @param adminName The admin username/name
     * @return true if admin exists, false otherwise
     */
    fun isValidAdminName(adminName: String): Boolean {
        return validAdmins.any { it.adminName.equals(adminName, ignoreCase = true) }
    }

    /**
     * Get all admin names for display purposes
     */
    fun getAllAdminNames(): List<String> {
        return validAdmins.map { it.adminName }
    }
}
