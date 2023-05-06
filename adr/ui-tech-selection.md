# Technology selection for UI

## Question: Which technology to use for building UI?

## Options
1. Separate API and UI and deploy them separately
2. Separate API and UI, package as a single artifact and deploy
3. Use a Server Side Templating library like Thymeleaf

## Decision
Use Thymeleaf with HTMX, AlpineJS.

## Reasons
* Keep the tech stack as minimal as possible while being able to achieve the application needs
* Simpler deployment process
* Using separate SPA UI needs NodeJS setup and brings all its headaches( and benefits too)
* Easier to implement SEO (if one day this app becomes popular, and we care about SEO)

## Consequences
* The web layer and UI are coupled
* Can't simply switch Backend/UI with different tech stacks
