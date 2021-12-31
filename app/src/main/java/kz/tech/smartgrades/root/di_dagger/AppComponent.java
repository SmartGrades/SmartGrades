package kz.tech.smartgrades.root.di_dagger;

import javax.inject.Singleton;

import dagger.Component;
import kz.tech.smartgrades.auth.AuthActivity;
import kz.tech.smartgrades.child.ChildActivity;
import kz.tech.smartgrades.mentor.MentorActivity;
import kz.tech.smartgrades.parent.ParentActivity;
import kz.tech.smartgrades.parent_add_child.ParentAddChildActivity;
import kz.tech.smartgrades.parent_add_mentor_or_sponsor.ParentAddSponsorOrMentorActivity;
import kz.tech.smartgrades.school.SchoolActivity;
import kz.tech.smartgrades.sponsor.SponsorActivity;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(AuthActivity activity);

    void inject(ChildActivity activity);
    void inject(MentorActivity activity);
    void inject(SponsorActivity sponsorActivity);
    void inject(SchoolActivity schoolActivity);

    void inject(ParentActivity activity);
    void inject(ParentAddChildActivity activity);
    void inject(ParentAddSponsorOrMentorActivity activity);
}
