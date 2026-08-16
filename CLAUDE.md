Project folders

src - spring boot java backend
admin - admin panel based on next.js

# admin panel
admin panel consists from 
1. sidebar menu
2. list of tags screen
3. list of translations screen
Each list screen should contain edit modal windows
## list of tags screen
List of tags screen should show list of tags stored represented by jpa entity org.maslov.bot.app.model.tag.Tag
API for tag described in class org.maslov.bot.app.tag.controller.TagController

## list of translations screen 
List of tags screen should show list of translations represented by jpa entity org.maslov.bot.app.model.Translation
API for translation described org.maslov.bot.app.tag.controller.TranslationController