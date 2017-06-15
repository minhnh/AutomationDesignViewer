package com.siemens.automationdesignviewer.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.siemens.automationdesignviewer.R;
import com.siemens.automationdesignviewer.holder.IconItemHolder;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AutomationDesignViewerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AutomationDesignViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutomationDesignViewerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private AndroidTreeView treeView;
//    private TextView statusBar;

    private TreeNode.TreeNodeClickListener nodeClickListener = new TreeNode.TreeNodeClickListener() {
        @Override
        public void onClick(TreeNode node, Object value) {
            IconItemHolder.IconItem item = (IconItemHolder.IconItem) value;
//            statusBar.setText(item.text);
        }
    };

    public AutomationDesignViewerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AutomationDesignViewerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AutomationDesignViewerFragment newInstance(String param1, String param2) {
        AutomationDesignViewerFragment fragment = new AutomationDesignViewerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_automation_design_viewer, container, false);
        ViewGroup containerView = rootView.findViewById(R.id.container);

//        statusBar = rootView.findViewById(R.id.status_bar);

        TreeNode root = TreeNode.root();
        TreeNode computerRoot = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "My Computer"));

        TreeNode myDocuments = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "My Documents"));
        TreeNode downloads = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "Downloads"));
        TreeNode file1 = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "Folder 1"));
        TreeNode file2 = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "Folder 2"));
        TreeNode file3 = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "Folder 3"));
        TreeNode file4 = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "Folder 4"));
        downloads.addChildren(file1, file2, file3, file4);

        TreeNode myMedia = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "Photos"));
        TreeNode photo1 = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "Folder 1"));
        TreeNode photo2 = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "Folder 2"));
        TreeNode photo3 = new TreeNode(new IconItemHolder.IconItem(R.string.ic_work, "Folder 3"));
        myMedia.addChildren(photo1, photo2, photo3);

        myDocuments.addChild(downloads);
        computerRoot.addChildren(myDocuments, myMedia);

        root.addChildren(computerRoot);

        treeView = new AndroidTreeView(getActivity(), root);
        treeView.setDefaultAnimation(true);
        treeView.setUse2dScroll(true);
        treeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        treeView.setDefaultViewHolder(IconItemHolder.class);
        treeView.setDefaultNodeClickListener(nodeClickListener);
        containerView.addView(treeView.getView());

        if (savedInstanceState != null) {
            String state = savedInstanceState.getString("tState");
            if (!TextUtils.isEmpty(state)) {
                treeView.restoreState(state);
            }
        }
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.expandAll:
                treeView.expandAll();
                break;
            case R.id.collapseAll:
                treeView.collapseAll();
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

class ProjectXmlParser{
    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            return readProject(parser);
        } finally {
            in.close();
        }
    }

    private List<EngineeringObject> readProject(XmlPullParser parser) throws XmlPullParserException, IOException {
        return null;
    }
}

class EngineeringObject {
    private String name;
    private String type;
    private ArrayList<String> names = new ArrayList();
    private HashMap<String, EngineeringObject> children = new HashMap();

    EngineeringObject(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void addChild(EngineeringObject child) {
        children.put(child.getName(), child);
        if (!names.add(child.getName())) {
            //TODO: handles error
        }
    }

    public EngineeringObject getChild(int index) {
        return children.get(names.get(index));
    }

    public Map<String, EngineeringObject> getChildren() {
        return children;
    }
}
