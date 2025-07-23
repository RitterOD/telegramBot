
POST("http://localhost:8080/tag") {
    header("Content-Type", "application/json")
    body(
        """
        {
            "name": "another tag5",
            "description": "another tag description5"
        }
        """.trimIndent()
    )

}

GET("http://localhost:8080/tag") {

}