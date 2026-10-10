# Project Review Presentation

## Deliverables

- `Personal_Finance_Manager_Review.pptx` — editable 13-slide deck.
- `Personal_Finance_Manager_Review.pdf` — exported review copy.

The deck covers the product workflows, dashboard composition, populated expense/budget/goal screens, protected admin screen, role model, database schema, Java web architecture, security boundaries, local setup, and a proposed team contribution split. The split gives Sachin a backend/security stretch task around budget reconciliation and regression tests. It records seven passing JUnit tests and identifies remaining integration-test work. The source repository URL is intentionally not included; add the public GitHub link when it is available.

## Regenerate

Run `python build_presentation.py` from this directory. The generator requires `python-pptx` and Pillow. To refresh the PDF after changing slides, open the generated PPTX in Microsoft PowerPoint and export it as PDF.

Screenshots in `assets/` are real local app captures. Populated finance rows and the admin screenshot account are temporary; the rows/account are removed from the local demo database after export. The dashboard hero, KPI strip, and quick-action panel are captured as separate screen regions and composed on one slide.