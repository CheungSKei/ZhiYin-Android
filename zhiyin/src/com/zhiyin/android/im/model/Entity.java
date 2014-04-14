package com.zhiyin.android.im.model;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.zhiyin.android.im.model.Entity.Builder.Factory;

/**
 * 所有数据实体的基类.<br>
 * 同时实现Parcelable接口
 * <p>
 * 如果子类要根据JsonObject实例化一个对象, 则需要实现 {@link Entity.Builder}接口,
 * 并提供getBuilder()方法返回实体构造器.
 * </p>
 * 
 * @version 2.0.0
 * @date 2014-1-28
 * @author S.Kei.Cheung
 */
public abstract class Entity implements Parcelable, Serializable {

	private static final long serialVersionUID = -2294312425440873364L;

	private JSONObject mInnerJSONObject;

	/**
	 * Gets the underlying JSONObject representation of this entity object.
	 * 
	 * @return the underlying JSONObject representation of this entity object
	 */
	public JSONObject getInnerJSONObject() {
		if (mInnerJSONObject == null) {
			mInnerJSONObject = new JSONObject();
		}
		return mInnerJSONObject;
	};

	@Override
	public int describeContents() {

		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			dest.writeValue(field);
		}
	}

	/**
	 * 数据实体构造器, 提供实例化方法.
	 * 
	 * @param <T>
	 *            具体的子类数据实体对象
	 * @version 1.0.0
	 * @date 2014-1-28
	 * @author S.Kei.Cheung
	 */
	public interface Builder {

		/**
		 * Creates proxies that implement GraphObject, GraphObjectList, and
		 * their derived types. These proxies allow access to underlying
		 * collections and name/value property bags via strongly-typed property
		 * getters and setters.
		 * <p/>
		 * This supports get/set properties that use primitive types, JSON
		 * types, Date, other GraphObject types, Iterable, Collection, List, and
		 * GraphObjectList.
		 */
		final class Factory {
			// No objects of this type should exist.
			private Factory() {
			}

			/**
			 * Creates a GraphObject proxy that provides typed access to the
			 * data in an underlying JSONObject.
			 * 
			 * @param json
			 *            the JSONObject containing the data to be exposed
			 * @return a GraphObject that represents the underlying data
			 * 
			 * @throws com.facebook.FacebookException
			 *             If the passed in Class is not a valid GraphObject
			 *             interface
			 */
			public static Entity create(JSONObject json) {
				return create(json, Entity.class);
			}

			/**
			 * Creates a Entity-derived proxy that provides typed access to the
			 * data in an underlying JSONObject.
			 * 
			 * @param json
			 *            the JSONObject containing the data to be exposed
			 * @param graphObjectClass
			 *            the GraphObject-derived type to return
			 * @return a graphObjectClass that represents the underlying data
			 * 
			 * @throws com.facebook.FacebookException
			 *             If the passed in Class is not a valid GraphObject
			 *             interface
			 */
			public static <T extends Entity> T create(JSONObject json,
					Class<T> entityClass) {
				Gson gson = new Gson();
				return gson.fromJson(json.toString(), entityClass);
			}

			/**
			 * Creates a GraphObject proxy that initially contains no data.
			 * 
			 * @return a GraphObject with no data
			 * 
			 * @throws com.facebook.FacebookException
			 *             If the passed in Class is not a valid GraphObject
			 *             interface
			 */
			public static Entity create() {
				try {
					return create(Entity.class);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
				return null;
			}

			/**
			 * Creates a GraphObject-derived proxy that initially contains no
			 * data.
			 * 
			 * @param graphObjectClass
			 *            the GraphObject-derived type to return
			 * @return a graphObjectClass with no data
			 * @throws InstantiationException
			 * @throws IllegalAccessException
			 * 
			 */
			public static <T extends Entity> T create(Class<T> entityClass)
					throws IllegalAccessException, InstantiationException {
				return entityClass.newInstance();
			}
		}
	}

	/**
	 * 创建Parcelable中的CREATOR实例，所有Entity的子类不需要重写该方法，
	 * 但需要保证其子类中所有set方法的赋值对象都会在writeToParcel中得到保存
	 * <p/>
	 * 该方法通过取得子类中所有的set方法，重新通过赋值方式重置数据。
	 */
	public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>() {

		@Override
		public Entity createFromParcel(Parcel source) {
			// 返回其实例
			Entity entity = (Entity) Factory.create();
			// 所有方法
			Method[] methods = entity.getClass().getMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				int parameterCount = method.getParameterTypes().length;
				Class<?> returnType = method.getReturnType();
				// 所有set方法
				if (parameterCount == 1 && returnType == Void.TYPE) {
					if (methodName.startsWith("set") && methodName.length() > 3) {
						// 重新赋值
						try {
							method.invoke(entity, source.readValue(method
									.getParameterTypes()[0].getClassLoader()));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
			return entity;
		}

		@Override
		public Entity[] newArray(int size) {
			return new Entity[size];
		}
	};

	/**
	 * 字符串输出
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public String toString() {
		// 当前类所有私有变量
		Method[] methods = this.getClass().getDeclaredMethods();
		// 允许访问
		AccessibleObject.setAccessible(methods, true);
		for (Method method : methods) {
			if (method.getName().startsWith("get")
					&& !getInnerJSONObject().has(method.getName().substring(3))
					&& method.getName().substring(3).length()>0
					&& method.getParameterTypes().length == 0){
				try {
					// 当输出字符串时，不存在的变量需要加入到Json当中
					Object obecjt = method.invoke(this);
					getInnerJSONObject().put(method.getName().substring(3),obecjt == null ? ""
					: (obecjt instanceof Entity ? new JSONObject(obecjt.toString())
					: (obecjt instanceof Map ? new JSONObject((Map) obecjt)
					: (obecjt instanceof JSONTokener ? new JSONObject((JSONTokener) obecjt)
					: String.valueOf(obecjt)))));
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}

		return getInnerJSONObject().toString();
	}
}
