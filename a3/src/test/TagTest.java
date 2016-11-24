/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import backend.Tag;
import backend.User;

/**
 * @author Vugar Mammadli
 *
 */
public class TagTest {

	private Tag uoftTag;
	private Tag torontoTag;
	private Tag cscTag;
	private List<Tag> allTags;
	private User user;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		uoftTag = new Tag("uoft");
		torontoTag = new Tag("toronto");
		cscTag = new Tag("csc");
		allTags = new ArrayList<>();
		allTags.add(uoftTag);
		allTags.add(torontoTag);
		allTags.add(cscTag);
		user = new User();
		Tag.setAllTags(new ArrayList<Tag>());
	}

	/**
	 * Test method for {@link backend.Tag#getName()}.
	 */
	@Test
	public void testGetName() {
		String actual = uoftTag.getName();
		String expected = "uoft";
		assertEquals(expected, actual);

		actual = torontoTag.getName();
		expected = "toronto";
		assertEquals(expected, actual);

		actual = cscTag.getName();
		expected = "csc";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link backend.Tag#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		uoftTag.setName(uoftTag.getName() + "x");
		String actual = uoftTag.getName();
		String expected = "uoftx";
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link backend.Tag#getAllTags()}.
	 */
	@Test
	public void testGetAllTagsNull() {
		List<Tag> actual = Tag.getAllTags();
		List<Tag> expected = new ArrayList<>();
		assertEquals(actual, expected);
	}

	/**
	 * Test method for {@link backend.User#addTag(String)} and
	 * {@link backend.Tag#getAllTags()}.
	 */
	@Test
	public void testGetAllTagsWithMultipleTags() {
		user.addTag(uoftTag.getName());
		user.addTag(torontoTag.getName());
		user.addTag(cscTag.getName());

		List<Tag> actual = Tag.getAllTags();
		List<Tag> expected = this.allTags;

		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for {@link backend.User#addTag(String)} and
	 * {@link backend.Tag#getAllTags()}.
	 */
	@Test
	public void testAddTagWithExistedTag() {
		user.addTag(uoftTag.getName());
		user.addTag(torontoTag.getName());
		user.addTag(uoftTag.getName());

		System.out.println(Tag.getAllTags());

		List<Tag> actual = Tag.getAllTags();
		this.allTags.remove(2);
		List<Tag> expected = this.allTags;

		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link backend.Tag#setAllTags(java.util.List)}.
	 */
	@Test
	public void testSetAllTagsNull() {
		Tag.setAllTags(new ArrayList<Tag>());
		List<Tag> actual = Tag.getAllTags();
		List<Tag> expected = new ArrayList<Tag>();

		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for {@link backend.Tag#setAllTags(java.util.List)}.
	 */
	@Test
	public void testSetAllTags() {
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(torontoTag);

		Tag.setAllTags(tags);

		List<Tag> actual = Tag.getAllTags();
		List<Tag> expected = new ArrayList<Tag>(Arrays.asList(this.torontoTag));

		assertEquals(expected, actual);
	}
}
