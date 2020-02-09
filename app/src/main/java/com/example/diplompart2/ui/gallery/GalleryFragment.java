package com.example.diplompart2.ui.gallery;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diplompart2.R;
import com.example.diplompart2.analyze_fragments.room.App;
import com.example.diplompart2.analyze_fragments.room.Converters;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Dao;
import com.example.diplompart2.analyze_fragments.room.static_one.EmployeeStatic1Database;
import com.example.diplompart2.analyze_fragments.room.static_two.EmployeeStatic2;
import com.example.diplompart2.analyze_fragments.static_analyze_2.TypeAtribute2;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class GalleryFragment extends Fragment {
    //recycleView
    private RecyclerView recyclerView;
    List<TypeAtribute2> typeList;
    //Convert
    Converters converters =new Converters();
    // ProgressBar
    ProgressBar progressBarList;
    Sprite ThreeBounce = new ThreeBounce(); // sprite for animation

    private GalleryViewModel galleryViewModel;
    private EmployeeStatic1Database db = App.getInstance().getDatabase(); // получение базы данных
    private EmployeeStatic1Dao employeeStatic1Dao = db.employeeStatic1Dao(); // get dao


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        //recyclerView
        recyclerView = root.findViewById(R.id.recyclerStatic);
        progressBarList = root.findViewById(R.id.progressList);

        progressBarList.setVisibility(VISIBLE);
        progressBarList.setIndeterminateDrawable(ThreeBounce);

      //  final TextView textView = root.findViewById(R.id.text_gallery);

//        try {
//            //initData();
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        complete();






      //  getDataFromRoom(1);

        return root;
    }


    public void getStatic1Data(EmployeeStatic1Database db, TextView textView){
        db.employeeStatic1Dao().getAll2()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<EmployeeStatic1>() {
                    @Override
                    public void onSuccess(EmployeeStatic1 employee) {
                        textView.setText(employee.model + " " + employee.imei + " " + employee.system+ " " + employee.root);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // ...
                    }
                });
    }

    private void initRecyclerView() {
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        StaticAdapter staticAdapter = new StaticAdapter(typeList);
        recyclerView.setAdapter(staticAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void initData() throws PackageManager.NameNotFoundException {

       // typeList.add(new TypeAtribute2("dsd","Iron Man", "7.9", "2008",ico("com.vkontakte.android"),"After being held captive in an Afghan cave, billionaire engineer Tony Stark creates a unique weaponized suit of armor to fight evil."));
//        typeList.add(new TypeAtribute2( "dsds","The Incredible Hulk", "6.7", "2008", ico("com.vkontakte.android"),"Bruce Banner, a scientist on the run from the U.S. Government, must find a cure for the monster he turns into, whenever he loses his temper."));
//        typeList.add(new TypeAtribute2( "dsds","Iron Man 2", "7.0", "2010", ico("com.vkontakte.android"),"With the world now aware of his identity as Iron Man, Tony Stark must contend with both his declining health and a vengeful mad man with ties to his father's legacy."));
//        typeList.add(new TypeAtribute2( "dsds","Thor", "7.0", "2011", ico("com.vkontakte.android"),"The powerful but arrogant god Thor is cast out of Asgard to live amongst humans in Midgard (Earth), where he soon becomes one of their finest defenders."));
//        typeList.add(new TypeAtribute2( "dsds","Captain America: The First Avenger", "6.9", "2011", ico("com.vkontakte.android"),"Steve Rogers, a rejected military soldier transforms into Captain America after taking a dose of a Super-Soldier serum. But being Captain America comes at a price as he attempts to take down a war monger and a terrorist organization."));
//        typeList.add(new TypeAtribute2( "dsds","The Avengers", "8.0", "2012", ico("com.vkontakte.android"),"Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity."));
//        typeList.add(new TypeAtribute2( "dsds","Iron Man 3","7.2","2013",ico("com.vkontakte.android"),"When Tony Stark's world is torn apart by a formidable terrorist called the Mandarin, he starts an odyssey of rebuilding and retribution."));
//        typeList.add(new TypeAtribute2( "dsds","Thor: The Dark World","6.9","2013",ico("com.vkontakte.android"),"When the Dark Elves attempt to plunge the universe into darkness, Thor must embark on a perilous and personal journey that will reunite him with doctor Jane Foster."));
//        typeList.add(new TypeAtribute2( "dsds","Captain America: The Winter Soldier","7.8","2014",ico("com.vkontakte.android"),"As Steve Rogers struggles to embrace his role in the modern world, he teams up with a fellow Avenger and S.H.I.E.L.D agent, Black Widow, to battle a new threat from history: an assassin known as the Winter Soldier."));
//        typeList.add(new TypeAtribute2( "dsds", "dd","7.3","2015",ico("com.vkontakte.android"),"When Tony Stark and Bruce Banner try to jump-start a dormant peacekeeping program called Ultron, things go horribly wrong and it's up to Earth's mightiest heroes to stop the villainous Ultron from enacting his terrible plan."));
    }

    private Drawable ico(String app) throws PackageManager.NameNotFoundException {
        Drawable appIco =  Objects.requireNonNull(getActivity()).getPackageManager().getApplicationIcon(app);
        return appIco;
    }

    private void getDataFromRoom(int i) throws PackageManager.NameNotFoundException {

        EmployeeStatic2 employee = employeeStatic1Dao.getById(i);
        typeList.add(new TypeAtribute2(employee.apkName,
                employee.apkFullName,
                employee.apkVersion,
                employee.apkPath,
                ico(employee.apkFullName),
                getConvertListPermission(converters.fromString((employee.apkPermission))).toString()));

        System.out.println(getConvertListPermission(converters.fromString(employee.apkPermission))  +"            Thread ->   " +Thread.currentThread().getName());


    }

    private void complete(){
        typeList = new ArrayList<>();
        Observable.range(1,35)
                .subscribeOn(Schedulers.io())
                .doOnNext(this::getDataFromRoom)
                .observeOn(AndroidSchedulers.mainThread())

               // .doOnNext(this::initRecyclerView)
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        progressBarList.stopNestedScroll();
                        progressBarList.setVisibility(INVISIBLE);
                        initRecyclerView();
                    }
                });
    }


    private StringBuilder getConvertListPermission( ArrayList<String> perm){

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < perm.size(); i++){
            s.append(i+1 + ") " + perm.get(i) + "\n");
        }
            return s;

    }

}