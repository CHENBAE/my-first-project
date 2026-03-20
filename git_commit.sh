#!/bin/bash

# 进入git仓库目录
cd ..

echo "正在执行git add..."
git add gs/front/src/views/Affair.vue gs/front/src/views/Announcement.vue gs/front/src/views/Department.vue gs/front/src/views/Home.vue gs/front/src/views/Leader.vue gs/front/src/views/Meeting.vue gs/front/src/views/PartyBranch.vue gs/front/src/views/PartyMember.vue gs/front/src/views/Person.vue gs/front/src/views/TimeNode.vue

echo "正在执行git commit..."
git commit -m "更新图片和logo，修复操作栏按钮对齐问题"

echo "正在执行git push..."
git push origin main

echo "操作完成！"
